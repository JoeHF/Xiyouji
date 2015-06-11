package com.xiyouji.app.MainLogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by houfang on 15/4/28.
 */
public class WantWashingActivity extends Activity {
    private CheckBox wash_immediately, wash_order, wash_out, wash_inout;
    private TextView carInfo, phone, carLoc;

    private String carId = "", siteId = "", type = "";
    private String username, password;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_washing);
        wash_immediately = (CheckBox) findViewById(R.id.wash_immediately);
        wash_order = (CheckBox) findViewById(R.id.wash_order);
        wash_out = (CheckBox) findViewById(R.id.wash_outside);
        wash_inout = (CheckBox) findViewById(R.id.wash_inout);
        carInfo = (TextView) findViewById(R.id.car_info);
        phone = (TextView) findViewById(R.id.phone);
        carLoc = (TextView) findViewById(R.id.car_loc);


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_waitwash(View v) {


        Calendar c = Calendar.getInstance();
        String createtime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND);
        String asktime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + c.get(Calendar.DAY_OF_MONTH) + " " + hour + ":" + minute + ":00";


        RequestParams requestParams = new RequestParams();
        Log.i("add order", "carid:" + carId + " siteid:" + siteId + " createtime:" + createtime + " phone:" + username + " password:" + password + " type:" + type);
        requestParams.put("carid", carId);
        requestParams.put("siteid", siteId);
        requestParams.put("createtime", createtime);
        if (wash_order.isChecked()) {//预约订单
            requestParams.put("asktime", asktime);
        }
        requestParams.put("phone", username);
        requestParams.put("password", password);
        requestParams.put("type", type);

        RestClient.post(Constant.ADD_ORDER, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("make order", response.toString());
                try {
                    if (response.getString("stage").equals("success")) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", response.getString("orderid"));
                        intent.putExtras(bundle);
                        intent.setClass(WantWashingActivity.this, WaitWashingActivity.class);
                        //intent.setClass(this, xiaoerInfoActivity.class);  //test code
                        //intent.setClass(this, PayJudgeActivity.class);  //test code
                        startActivityForResult(intent, Constant.START_ORDER);
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
        if (v.getId() == wash_immediately.getId()) {
            wash_immediately.setChecked(true);
            wash_order.setChecked(false);
            findViewById(R.id.wash_order_time).setVisibility(View.GONE);
        } else {
            wash_immediately.setChecked(false);
            wash_order.setChecked(true);
            findViewById(R.id.wash_order_time).setVisibility(View.VISIBLE);
            //TODO

            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    c.setTimeInMillis(System.currentTimeMillis());
//                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    c.set(Calendar.MINUTE, minute);
//                    c.set(Calendar.SECOND, 0);
//                    c.set(Calendar.MILLISECOND, 0);
                    hourOfDay = hourOfDay;
                    minute = minute;
                    ((TextView) findViewById(R.id.order_time)).setText("预约时间： " +
                            (hourOfDay + 1) + ":" + minute);
                }
            }, hour, minute, true).show();

        }
    }

    public void click_wash_inout(View v) {
        wash_out.setChecked(false);
        wash_inout.setChecked(false);
        ((CheckBox) v).setChecked(true);
        type = ((CheckBox) v).getTag().toString();
        if (type.equals("2")) {//外部
            new AlertDialog.Builder(this).setTitle("提示").setMessage("请在车辆旁等待，并将车钥匙交给店小二").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
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
                        username = bundle.getString("phone");
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
            case Constant.START_ORDER:
                switch (resultCode) {
                    case Constant.START_ORDER_SUCCESS_BACK:
                        Bundle bundle = data.getExtras();
                        String status = bundle.getString("status");
                        if (status.equals("success")) {
                            Log.i("Order has been issued", "success");
                            finish();
                        }
                    default:
                        break;
                }
            default:
                break;
        }
    }
}
