package com.xiyouji.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by houfang on 2015/5/2.
 */
public class PayJudgeActivity extends Activity {
    //ui
    private TextView title, right; //Action Bar
    private CheckBox pay_weixin, pay_ali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_judge);
        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);
        pay_weixin = (CheckBox)findViewById(R.id.pay_weixin);
        pay_ali = (CheckBox)findViewById(R.id.pay_ali);
        title.setText("支付与评价");
        right.setText("投诉");
    }

    public void click_choose_payment(View v) {
        if(v.getTag().equals("weixin")) {
            pay_weixin.setChecked(true);
            pay_ali.setChecked(false);
        }
        else {
            pay_weixin.setChecked(false);
            pay_ali.setChecked(true);
        }
    }
}
