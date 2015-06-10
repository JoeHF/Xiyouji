package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.PayJudgeActivity;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;
import com.xiyouji.app.xiaoerInfoActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by houfang on 2015/4/29.
 */
public class WaitWashingActivity extends Activity {
    private Timer timer = new Timer();
    private int totalTime = 10;

    private String orderId;

    private TextView timeValue;
    private RequestParams requestParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_washing);
        Bundle bundle = getIntent().getExtras();

        timeValue = (TextView)findViewById(R.id.time);

        orderId = bundle.getString("orderId");
        requestParams = new RequestParams();
        requestParams.put("orderid", orderId);
        timer.schedule(task, 1000, 1000);

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    totalTime--;
                    int minute = totalTime / 60;
                    int second = totalTime % 60;
                    RestClient.post(Constant.GET_ORDER_DETAIL, requestParams, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.i("get order detail", response.toString());
                            try {
                                if (!response.getString("stage").equals("待接单")) {
                                    String waiterId = response.getString("waiterid");
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("waiterId", waiterId);
                                    bundle.putString("orderId", orderId);
                                    intent.putExtras(bundle);
                                    intent.setClass(WaitWashingActivity.this, WaitWaiterActivity.class);  //test code
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in,
                                            R.anim.push_left_out	);
                                    timer.cancel();
                                    intent = new Intent();
                                    bundle = new Bundle();
                                    bundle.putString("status", "success");
                                    intent.putExtras(bundle);
                                    setResult(Constant.START_ORDER_SUCCESS_BACK, intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    if(second >= 10) {
                        timeValue.setText(minute + ":" + second);
                    }
                    else {
                        timeValue.setText(minute + ":0" + second);
                    }

                    if(totalTime <= 0){
                        timer.cancel();
                    }
                }
            });
        }
    };

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_right(View v) {
        Intent intent = new Intent();
        intent.setClass(this, xiaoerInfoActivity.class);  //test code
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
}
