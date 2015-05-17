package com.xiyouji.app;

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

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_city(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, CityChooseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
    public void click_to_icon(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, IconChooseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
    public void click_to_color(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, ColorChooseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
}
