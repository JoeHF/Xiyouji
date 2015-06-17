package com.xiyouji.app.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.MainActivity;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 15/5/29.
 */
public class LoginActivity extends Activity {
    private EditText username, password;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在登录");
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        SharedPreferences mySharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        String name = mySharedPreferences.getString("username","0");
        String pass = mySharedPreferences.getString("password","0");
        if(!name.equals("0")){
            mProgressDialog.show();
            login(name, pass);
        }
    }

    public void click_to_homepage(View v) {
        mProgressDialog.show();
        final String username_value = username.getText().toString();
        final String password_value = password.getText().toString();
        login(username_value, password_value);
    }

    public void login(final String username_value, final String password_value) {
        RequestParams requestParams = new RequestParams();

        //for test
        //final String username_value = "123";
        //final String password_value = "123";
        requestParams.put("phone", username_value);
        requestParams.put("password", password_value);

        RestClient.post(Constant.LOGIN, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("http login jsonobject", response.toString());
                try {
                    String money = response.getString("money");
                    String id = response.getString("userid");
                    String phone = response.getString("phone");
                    String longitude = response.getString("long");
                    String latitude = response.getString("lat");
                    String nickname = response.getString("nickname");

                    //实例化SharedPreferences对象（第一步）
                    SharedPreferences mySharedPreferences = getSharedPreferences("user",
                            Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("id", id);
                    editor.putString("phone", phone);
                    editor.putString("longitude", longitude);
                    editor.putString("latitude", latitude);
                    editor.putString("money", money);
                    editor.putString("username", username_value);
                    editor.putString("password", password_value);
                    editor.putString("nickname", nickname);

                    //提交当前数据
                    editor.commit();

                    Intent intent1 = new Intent();
                    intent1.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    mProgressDialog.dismiss();
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    finish();
                } catch (JSONException e) {
                    //new AlertDialog.Builder(LoginActivity.this).setTitle("提示信息").setMessage("登陆失败").show();
                    e.printStackTrace();
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "用户名密码不匹配", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("qqq","Login failure "+ responseString);
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "网络连接有问题", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "网络连接有问题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click_to_register(View v) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivityForResult(intent, Constant.START_REGISTER);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);

    }

    protected void onActivityResult(final int requestCode, int resultCode, Intent intent) {
        if(requestCode == Constant.START_REGISTER) {
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case Constant.START_REGISTER_BACK:
                    Bundle bundle = intent.getExtras(); //data为B中回传的Intent
                    String status = bundle.getString("status");//str即为回传的值
                    String username = bundle.getString("username");
                    String password = bundle.getString("password");
                    if(status.equals("success")) {
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("phone", username);
                        requestParams.put("password", password);

                        RestClient.get(Constant.LOGIN, requestParams, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                Log.i("http login jsonobject", response.toString());
                                try {
                                    String money = response.getString("money");
                                    String id = response.getString("userid");
                                    String phone = response.getString("phone");
                                    String longitude = response.getString("long");
                                    String latitude = response.getString("lat");
                                    String nickname = response.getString("nickname");

                                    //实例化SharedPreferences对象（第一步）
                                    SharedPreferences mySharedPreferences= getSharedPreferences("user",
                                            Activity.MODE_PRIVATE);
                                    //实例化SharedPreferences.Editor对象（第二步）
                                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                                    //用putString的方法保存数据
                                    editor.putString("id", id);
                                    editor.putString("phone", phone);
                                    editor.putString("longitude", longitude);
                                    editor.putString("latitude", latitude);
                                    editor.putString("money", money);
                                    editor.putString("nickname", nickname);
                                    //提交当前数据
                                    editor.commit();

                                    Intent intent1 = new Intent();
                                    intent1.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent1);
                                    overridePendingTransition(R.anim.push_left_in,
                                            R.anim.push_left_out	);
                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                    break;
                default:
                    break;
            }
        }
    }
}
