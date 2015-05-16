package com.xiyouji.xiyouji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by houfang on 15/4/28.
 */
public class WantWashingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_washing);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_waitwash(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, WaitWashingActivity.class);
        //intent.setClass(this, xiaoerInfoActivity.class);  //test code
        //intent.setClass(this, PayJudgeActivity.class);  //test code
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
    public void click_to_information(View w)
    {
        Intent intent = new Intent();
        intent.setClass(this, CarInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
    public void click_to_location(View w)
    {
        Intent intent = new Intent();
        intent.setClass(this, CarlocActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
    public void click_to_number(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, NumberCheckActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
}
