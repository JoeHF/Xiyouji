package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Login.LoginActivity;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 15/6/12.
 */
public class AdviceActivity extends Activity {
    private EditText contact, content;
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice);
        contact = (EditText)findViewById(R.id.contact);
        content = (EditText)findViewById(R.id.content);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_advice(View v) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);
        requestParams.put("contact", contact.getText().toString());
        requestParams.put("content", content.getText().toString());

        RestClient.post(Constant.ADD_COMPLAIN, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("add advice", response.toString());
                try {
                    if (response.getString("state").equals("success")) {
                        new AlertDialog.Builder(AdviceActivity.this).setTitle("提示信息").setMessage("评论成功").show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
