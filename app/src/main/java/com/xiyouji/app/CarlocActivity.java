package com.xiyouji.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xiyouji.app.Constant.Constant;

/**
 * Created by houfang on 2015/4/29.
 */
public class CarlocActivity extends Activity{
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        location = (TextView)findViewById(R.id.location);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_lbs(View w)
    {
        Intent intent = new Intent();
        intent.setClass(this, CarLbsActivity.class);
        startActivityForResult(intent, Constant.START_LBS);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    //接收回传值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.START_LBS) {
            switch (resultCode) {
                //接收定位地址
                case Constant.START_LBS_BACK:
                    Bundle bundle = data.getExtras();
                    String location_addr = bundle.getString("location_addr");
                    location.setText(location_addr);
                    Log.i("location", location_addr);
                    break;
                default:
                    break;
            }
        }
    }

}