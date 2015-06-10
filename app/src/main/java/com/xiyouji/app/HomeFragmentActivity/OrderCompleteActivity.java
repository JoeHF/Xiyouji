package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
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
 * Created by houfang on 15/6/10.
 */
public class OrderCompleteActivity extends Activity {
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
        setContentView(R.layout.pay_judge_paid);
        Bundle bundle = getIntent().getExtras();
        waiterId = bundle.getString("waiterId");
        orderId = bundle.getString("orderId");

        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);
        xiaoerScoreValue = (TextView)findViewById(R.id.xiaoer_score);
        xiaoerNumValue = (TextView)findViewById(R.id.xiaoer_num);
        xiaoerIdValue = (TextView)findViewById(R.id.xiaoer_id);

        RequestParams requestParams = new RequestParams();
        requestParams.put("waiterid", waiterId);
        RestClient.post(Constant.GET_WAITER_INFO, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("waiter info", response.toString());
                try {
                    waiter.setWaiterId(response.getString("waiterid"));
                    waiter.setLatitude(response.getDouble("lat"));
                    waiter.setLongitude(response.getDouble("long"));
                    waiter.setStar(response.getString("star"));
                    waiter.setCount(response.getString("count"));
                    waiter.setCode(response.getString("code"));
                    xiaoerScoreValue.setText(waiter.getStar());
                    xiaoerNumValue.setText(waiter.getCount());
                    xiaoerIdValue.setText("小二" + waiter.getCode());
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
}
