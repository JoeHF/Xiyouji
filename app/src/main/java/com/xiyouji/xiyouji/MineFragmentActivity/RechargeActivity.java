package com.xiyouji.xiyouji.MineFragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.xiyouji.xiyouji.R;

/**
 * Created by houfang on 15/5/7.
 */
public class RechargeActivity extends Activity {
    private Dialog dialog;
    private TextView centerTitle, rechargeRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);
        centerTitle = (TextView)findViewById(R.id.title);
        rechargeRecord = (TextView)findViewById(R.id.right);
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

}
