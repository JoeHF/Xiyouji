package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 15/5/7.
 */
public class RechargeActivity extends Activity {
    private Dialog dialog;
    private TextView centerTitle, rechargeRecord, leftMoney;
    private CheckBox recharge300, recharge800, recharger1500;

    private String userId, username, password;
    private String rechargeMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
        username = user.getString("username", "0");
        password = user.getString("password", "0");

        centerTitle = (TextView)findViewById(R.id.title);
        rechargeRecord = (TextView)findViewById(R.id.right);
        leftMoney = (TextView)findViewById(R.id.left_money);
        recharge300 = (CheckBox)findViewById(R.id.recharge_300);
        recharge800 = (CheckBox)findViewById(R.id.recharge_800);
        recharger1500 = (CheckBox)findViewById(R.id.recharge_1500);
        centerTitle.setText("充值");
        rechargeRecord.setText("充值记录");

        GetRechargeData();
    }

    public void GetRechargeData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.post(Constant.GET_MONEY, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("get recharge array", responses.toString());
                try {
                    JSONObject jsonObject = responses.getJSONObject(0);
                    leftMoney.setText(jsonObject.getString("money"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_pay(View v) {
        if(rechargeMoney == null || rechargeMoney.length() == 0) {
            new AlertDialog.Builder(RechargeActivity.this).setTitle("提示信息").setMessage("请选择充值金额").show();
            return;
        }

        View view = View.inflate(this,R.layout.choose_payment_way, null);
        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void click_to_choose_payway(View v) {
        dialog.dismiss();
        String payway = v.getTag().toString();
        Log.i("recharge money", "money:" + rechargeMoney + " way:" + payway);
        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", username);
        requestParams.put("password", password);
        requestParams.put("money", rechargeMoney);
        requestParams.put("payway", payway);

        RestClient.post(Constant.RECHARGE, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("recharge", response.toString());
                new AlertDialog.Builder(RechargeActivity.this).setTitle("提示信息").setMessage("充值成功").show();
                GetRechargeData();
            }
        });
    }

    public void payment_cancel(View v) {
        dialog.dismiss();
    }

    public void click_right(View v) {
        Intent intent = new Intent();
        intent.setClass(this, RechargeRecordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_choose_recharge(View v) {
        recharge300.setChecked(false);
        recharge800.setChecked(false);
        recharger1500.setChecked(false);
        ((CheckBox)v).setChecked(true);
        rechargeMoney = ((CheckBox)v).getTag().toString();


    }

}
