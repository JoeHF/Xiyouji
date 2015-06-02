package com.xiyouji.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by houfang on 15/4/28.
 */
public class WantWashingActivity extends Activity {
    private CheckBox wash_immediately, wash_order, wash_out, wash_inout;
    private TextView carInfo, phone, carLoc;

    private String carId = "", siteId = "", type = "";
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_washing);
        wash_immediately = (CheckBox)findViewById(R.id.wash_immediately);
        wash_order = (CheckBox)findViewById(R.id.wash_order);
        wash_out = (CheckBox)findViewById(R.id.wash_outside);
        wash_inout = (CheckBox)findViewById(R.id.wash_inout);
        carInfo = (TextView)findViewById(R.id.car_info);
        phone = (TextView)findViewById(R.id.phone);
        carLoc = (TextView)findViewById(R.id.car_loc);

        SharedPreferences user = getSharedPreferences("user", 0);
        username = user.getString("username", "0");
        password = user.getString("password", "0");
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_waitwash(View v)
    {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);

        RequestParams requestParams = new RequestParams();
        Log.i("add order", "carid:" + carId + " siteid:" + siteId + " createtime:" + time + " phone:" + username + " password:" + password + " type:" + type);
        requestParams.put("carid", carId);
        requestParams.put("siteid", siteId);
        requestParams.put("createtime", time);
        requestParams.put("phone", username);
        requestParams.put("password", password);
        requestParams.put("type", type);

        RestClient.post(Constant.ADD_ORDER, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("make order", response.toString());
                try {
                    if(response.getString("state").equals("success")) {
                        Intent intent = new Intent();
                        intent.setClass(WantWashingActivity.this, WaitWashingActivity.class);
                        //intent.setClass(this, xiaoerInfoActivity.class);  //test code
                        //intent.setClass(this, PayJudgeActivity.class);  //test code
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

            public void click_to_information(View w) {
                Intent intent = new Intent();
                intent.setClass(this, CarInfoActivity.class);
                startActivityForResult(intent, Constant.START_CAR_INFO);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }

            public void click_to_location(View w) {
                Intent intent = new Intent();
                intent.setClass(this, CarlocActivity.class);
                startActivityForResult(intent, Constant.START_CAR_LOC);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }

            public void click_to_number(View v) {
                Intent intent = new Intent();
                intent.setClass(this, NumberCheckActivity.class);
                startActivityForResult(intent, Constant.START_PHONE_NUMBER);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }

            public void click_wash_type(View v) {
                wash_immediately.setChecked(false);
                wash_order.setChecked(false);
                ((CheckBox) v).setChecked(true);
            }

            public void click_wash_inout(View v) {
                wash_out.setChecked(false);
                wash_inout.setChecked(false);
                ((CheckBox) v).setChecked(true);
                type = ((CheckBox) v).getTag().toString();
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                // 根据上面发送过去的请求来区别
                switch (requestCode) {
                    case Constant.START_CAR_INFO:
                        switch (resultCode) {
                            case Constant.START_CAR_INFO_BACK:
                                Bundle bundle = data.getExtras();
                                carInfo.setText(bundle.getString("carInfo"));
                                carId = bundle.getString("carId");
                                break;
                            default:
                                break;
                        }
                        break;
                    case Constant.START_PHONE_NUMBER:
                        switch (resultCode) {
                            case Constant.START_PHONE_NUMBER_BACK:
                                Bundle bundle = data.getExtras();
                                phone.setText(bundle.getString("phone"));
                                break;
                            default:
                                break;
                        }
                    case Constant.START_CAR_LOC:
                        switch (resultCode) {
                            case Constant.START_CAR_LOC_BACK:
                                Bundle bundle = data.getExtras();
                                carLoc.setText(bundle.getString("carLoc"));
                                siteId = bundle.getString("siteId");
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
            }
        }
