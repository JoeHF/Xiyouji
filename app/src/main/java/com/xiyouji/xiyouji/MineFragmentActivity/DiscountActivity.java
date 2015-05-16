package com.xiyouji.xiyouji.MineFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.xiyouji.xiyouji.Adapter.DiscountsAdapter;
import com.xiyouji.xiyouji.Model.Discount;
import com.xiyouji.xiyouji.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/5/13.
 */
public class DiscountActivity extends Activity {
    private TextView title, right;
    private ListView view;

    private List<Discount> discounts;
    private DiscountsAdapter discountsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount);
        title = (TextView)findViewById(R.id.title);
        title.setText("优惠劵");

        view = (ListView)findViewById(R.id.discount_list);

        discounts = new ArrayList<Discount>();
        discounts.add(new Discount());
        discounts.add(new Discount());
        discountsAdapter = new DiscountsAdapter(discounts, DiscountActivity.this);

        view.setAdapter(discountsAdapter);
    }
}
