package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xiyouji.app.R;

/**
 * Created by houfang on 15/5/7.
 */
public class RechargeActivity extends Activity {
    private Dialog dialog;
    private TextView centerTitle, rechargeRecord;
    private CheckBox recharge300, recharge800, recharger1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);
        centerTitle = (TextView)findViewById(R.id.title);
        rechargeRecord = (TextView)findViewById(R.id.right);
        recharge300 = (CheckBox)findViewById(R.id.recharge_300);
        recharge800 = (CheckBox)findViewById(R.id.recharge_800);
        recharger1500 = (CheckBox)findViewById(R.id.recharge_1500);
        centerTitle.setText("充值");
        rechargeRecord.setText("充值记录");
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_pay(View v) {
        View view = View.inflate(this,R.layout.choose_payment_way, null);
        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void payment_cancel(View v) {
        dialog.dismiss();
    }

    public void click_right(View v) {
        Intent intent = new Intent();
        intent.setClass(this, RechargeRecordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_choose_recharge(View v) {
        recharge300.setChecked(false);
        recharge800.setChecked(false);
        recharger1500.setChecked(false);
        ((CheckBox)v).setChecked(true);

    }

}
