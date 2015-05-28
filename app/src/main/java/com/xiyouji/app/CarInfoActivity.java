package com.xiyouji.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

/**
 * Created by houfang on 2015/4/29.
 */
public class CarInfoActivity extends Activity{
    private Dialog dialog;

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
        View view = View.inflate(this,R.layout.choose_car_province, null);
        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();

    }
    public void click_to_icon(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, IconChooseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    //todo 改变车辆颜色
    public void click_to_color(View v)
    {

    }

    //todo 改变车辆牌子
    public void click_to_choose_car_brand(View v) {

    }

    //todo 保存车辆信息
    public void click_to_save_information(View v) {

    }

    public void click_to_cancel(View v) {
        dialog.dismiss();
    }
}
