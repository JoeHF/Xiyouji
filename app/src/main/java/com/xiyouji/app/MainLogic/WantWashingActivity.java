package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.DateTimePickDialogUtil;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by houfang on 15/4/28.
 */
public class WantWashingActivity extends Activity {
    private Timer timer = new Timer();
    private CheckBox wash_immediately, wash_order, wash_out, wash_inout;
    private TextView carInfo, phone, carLoc;
    private EditText askTimeValue, discountValue;

    private String carId = "", siteId = "", type = "", washTime = "";
    private String username, password;

    private String initStartDateTime; // 初始化开始时间
    private String askTime;
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
        askTimeValue = (EditText)findViewById(R.id.order_time);
        discountValue = (EditText)findViewById(R.id.discount);

        Calendar cal= Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        initStartDateTime = format.format(date);
        askTimeValue.setText(initStartDateTime);
        askTimeValue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        WantWashingActivity.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(askTimeValue);
            }
        });

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
        String time = date.getTime() + "";
        if (!washTime.equals("immediately")) {
            Log.i("wash time", askTimeValue.getText().toString());
            if (!(askTimeValue.getText() != null || askTimeValue.getText().length() != 0)) {
                new AlertDialog.Builder(WantWashingActivity.this).setTitle("提示信息").setMessage("请填写预约时间").show();
                return;
            }
        }

        //Log.i("Time:", date.getTime() + "");
        //DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String time = format.format(date);

        RequestParams requestParams = new RequestParams();
        Log.i("add order", "carid:" + carId + " siteid:" + siteId + " createtime:" + time + " phone:" + username + " password:" + password + " type:" + type);
        requestParams.put("carid", carId);
        requestParams.put("siteid", siteId);
        requestParams.put("createtime", time);
        requestParams.put("phone", username);
        requestParams.put("password", password);
        requestParams.put("type", type);
        if (discountValue.getText() != null && discountValue.getText().length() != 0) {
            requestParams.put("ticketid", discountValue.getText().toString());
        }

        if (washTime.equals("order")) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String asktime = askTimeValue.getText().toString();
                Date date2 = format.parse(asktime);
                Log.i("wash time", date2.getTime() + "");
                requestParams.put("asktime", date2.getTime() / 1000 + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        RestClient.post(Constant.ADD_ORDER, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("make order", response.toString());
                try {
                    if(response.getString("stage").equals("success")) {
                        if (!washTime.equals("immediately")) {
                            new AlertDialog.Builder(WantWashingActivity.this).setTitle("提示信息").setMessage("预约成功").show();
                            timer.schedule(task, 1000, 1000);
                            return;
                        }

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", response.getString("orderid"));
                        intent.putExtras(bundle);
                        intent.setClass(WantWashingActivity.this, WaitWashingActivity.class);
                        startActivityForResult(intent, Constant.START_ORDER);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("make order failure", responseString);
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
        washTime = ((CheckBox)v).getTag().toString();
        ((CheckBox) v).setChecked(true);
    }

    public void click_wash_inout(View v) {
        wash_out.setChecked(false);
        wash_inout.setChecked(false);
        ((CheckBox) v).setChecked(true);
        type = ((CheckBox) v).getTag().toString();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    timer.cancel();
                    finish();
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            });
        }
    };


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
