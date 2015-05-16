package com.xiyouji.xiyouji.MineFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.xiyouji.xiyouji.Adapter.RechargeAdapter;
import com.xiyouji.xiyouji.Model.Charge;
import com.xiyouji.xiyouji.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/5/13.
 */
public class RechargeRecordActivity extends Activity {
    private TextView centerTitle;
    private ListView recordsListView;

    private List<Charge> chargeList;
    private RechargeAdapter rechargeAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_record);
        centerTitle = (TextView)findViewById(R.id.title);
        recordsListView = (ListView)findViewById(R.id.recharge_list);

        chargeList = new ArrayList<Charge>();
        Charge c1 = new Charge();
        c1.setTag(false);
        c1.setDate("本月");
        chargeList.add(c1);
        chargeList.add(new Charge());
        chargeList.add(new Charge());
        Charge c2 = new Charge();
        c2.setTag(false);
        c2.setDate("4月");
        chargeList.add(c2);
        chargeList.add(new Charge());
        rechargeAdapter = new RechargeAdapter(chargeList, RechargeRecordActivity.this);

        recordsListView.setAdapter(rechargeAdapter);
        centerTitle.setText("充值记录");

    }
}
