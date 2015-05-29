package com.xiyouji.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
    }

    public void click_to_homepage(View v) {
        String username_value = username.getText().toString();
        String password_value = password.getText().toString();

        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", username_value);
        requestParams.put("password", password_value);

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
