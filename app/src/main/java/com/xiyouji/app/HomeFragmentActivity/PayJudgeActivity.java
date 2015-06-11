package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Waiter;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 2015/5/2.
 */
public class PayJudgeActivity extends Activity {
    //ui
    private TextView title, right; //Action Bar
    private CheckBox pay_weixin, pay_ali;

    private String waiterId;
    private String orderId;
    private Waiter waiter = new Waiter();

    private TextView xiaoerScoreValue, xiaoerNumValue, xiaoerIdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_judge);
        Bundle bundle = getIntent().getExtras();
        waiterId = bundle.getString("waiterId");
        orderId = bundle.getString("orderId");

        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);

        RequestParams requestParams = new RequestParams();
        requestParams.put("orderid", orderId);
        RestClient.post(Constant.GET_ORDER_DETAIL, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("get order detail", response.toString());
                try {
                    if (response.getString("stage").equals("服务中")) {
                        String waiterId = response.getString("waiterid");
                    }

                } catch (JSONException e) {
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

    public void click_choose_payment(View v) {
        if(v.getTag().equals("weixin")) {
            pay_weixin.setChecked(true);
            pay_ali.setChecked(false);
        }
        else {
            pay_weixin.setChecked(false);
            pay_ali.setChecked(true);
        }
    }
}
