package com.xiyouji.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
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
 * Created by houfang on 15/5/29.
 */
public class RegisterActivity extends Activity {
    private TextView title, right;
    private EditText username, password, password_repeat, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        password_repeat = (EditText)findViewById(R.id.password_repeat);
        nickname = (EditText)findViewById(R.id.nickname);

        title.setText("注册");
        right.setVisibility(View.GONE);
    }

    public void click_to_register(View v) {
        final String username_value = username.getText().toString();
        final String password_value = password.getText().toString();
        String password_repeat_value = password_repeat.getText().toString();
        String nickname_value = nickname.getText().toString();

        if(password_value.equals(password_repeat_value)) {
            RequestParams params = new RequestParams();
            params.put("phone", username_value);
            params.put("password", password_value);
            params.put("nickname", nickname_value);
            RestClient.get(Constant.REGISTER, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String status = response.getString("state");
                        if(status.equals("success")) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("status", status);
                            bundle.putString("username", username_value);
                            bundle.putString("password", password_value);
                            intent.putExtras(bundle);
                            setResult(Constant.START_REGISTER_BACK, intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                    Log.i("http:register JSONArray", responses.toString());

                }
            });
        }

    }
}
