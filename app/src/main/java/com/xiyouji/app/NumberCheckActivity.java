package com.xiyouji.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by houfang on 2015/4/29.
 */
public class NumberCheckActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_check);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

}