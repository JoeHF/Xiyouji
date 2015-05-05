package com.xiyouji.xiyouji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by houfang on 2015/4/29.
 */
public class CarInfoActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_car);
    }
    public void click_to_city(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, NumberCheckActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

}
