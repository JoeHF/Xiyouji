package com.xiyouji.app.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressDialog mProgressDialog;

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
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在注册");

        title.setText("注册");
        right.setVisibility(View.GONE);
    }

    public void click_to_register(View v) {
        final String username_value = username.getText().toString();
        if (username_value == null || username_value.length() == 0) {
            Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        final String password_value = password.getText().toString();
        if (password_value == null || password_value.length() == 0) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password_repeat_value = password_repeat.getText().toString();
        if (password_repeat_value == null || password_repeat_value.length() == 0) {
            Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String nickname_value = nickname.getText().toString();
        if (nickname_value == null || nickname_value.length() == 0) {
            Toast.makeText(RegisterActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }



        if(password_value.equals(password_repeat_value)) {
            mProgressDialog.show();
            Log.i("register", "begin to register");
            RequestParams params = new RequestParams();
            params.put("phone", username_value);
            params.put("password", password_value);
            params.put("nickname", nickname_value);
            RestClient.post(Constant.REGISTER, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    mProgressDialog.dismiss();
                    try {
                        Log.i("register", response.toString());
                        String status = response.getString("state");
                        if (status.equals("success")) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("status", status);
                            bundle.putString("username", username_value);
                            bundle.putString("password", password_value);
                            intent.putExtras(bundle);
                            setResult(Constant.START_REGISTER_BACK, intent);
                            finish();
                        } else {
                            //new AlertDialog.Builder(RegisterActivity.this).setTitle("提示信息").setMessage("注册失败").show();
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                    Log.i("http:register JSONArray", responses.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.e("qqq","Login failure "+ responseString);
                    mProgressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "网络连接有问题", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                    mProgressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "网络连接有问题", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            mProgressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
        }

    }
}
