package com.xiyouji.xiyouji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by houfang on 2015/4/29.
 */
public class WaitWashingActivity extends Activity{
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_washing);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_right(View v) {
        Intent intent = new Intent();
        intent.setClass(this, xiaoerInfoActivity.class);  //test code
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }
}
