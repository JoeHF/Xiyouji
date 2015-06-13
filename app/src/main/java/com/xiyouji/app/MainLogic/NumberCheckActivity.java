package com.xiyouji.app.MainLogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 2015/4/29.
 */
public class NumberCheckActivity extends Activity {
    private EditText phone, password;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_check);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
    }

    public void click_to_phone_check(View v) {
        try {
            String password_value = password.getText().toString();
            if(password_value.equals(code)) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone.getText().toString());
                intent.putExtras(bundle);
                setResult(Constant.START_PHONE_NUMBER_BACK, intent);
                finish();
                overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
            }
            else
            {
                new AlertDialog.Builder(NumberCheckActivity.this).setTitle("提示信息").setMessage("验证码或手机号码错误").show();
                TextView textview1=(TextView) findViewById(R.id.code);
                textview1.setText("获取");
                textview1.setClickable(true);

            }
        }
        catch (Exception e) {
            new AlertDialog.Builder(NumberCheckActivity.this).setTitle("提示信息").setMessage("验证码或手机号码错误").show();
            TextView textview1=(TextView) findViewById(R.id.code);
            textview1.setText("获取验证码");
            textview1.setClickable(true);
            e.printStackTrace();
        }

    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void get_identify(View v) {
        final String username_value = phone.getText().toString();
        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", username_value);
        RestClient.post(Constant.SEND_MSG, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("http login jsonobject", response.toString());
                try {
                    code = response.getString("code");
                    TextView textview1 = (TextView) findViewById(R.id.code);
                    textview1.setText("已获取");
                    textview1.setClickable(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}