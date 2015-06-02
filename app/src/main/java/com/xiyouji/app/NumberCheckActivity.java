package com.xiyouji.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xiyouji.app.Constant.Constant;

/**
 * Created by houfang on 2015/4/29.
 */
public class NumberCheckActivity extends Activity{
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_check);
        phone = (EditText)findViewById(R.id.phone);
    }

    public void click_to_phone_check(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone.getText().toString());
        intent.putExtras(bundle);
        setResult(Constant.START_PHONE_NUMBER_BACK, intent);
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    //todo 获取验证码
    public void get_identify(View v) {

    }

}