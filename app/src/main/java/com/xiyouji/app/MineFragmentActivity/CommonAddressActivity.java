package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiyouji.app.Adapter.AddressAdapter;
import com.xiyouji.app.Model.Address;
import com.xiyouji.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/5/17.
 */
public class CommonAddressActivity extends Activity{
    private TextView title;
    private ListView view;
    private ImageView add;
    private List<Address> addresses;
    private AddressAdapter addressAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_address);
        title = (TextView)findViewById(R.id.title);
        title.setText("常用地址");
        add = (ImageView)findViewById(R.id.right);
        add.setImageDrawable(getResources().getDrawable(R.drawable.dibiao));
        view = (ListView)findViewById(R.id.addr_list);

        addresses = new ArrayList<Address>();
        addresses.add(new Address());
        addresses.add(new Address());
        addressAdapter = new AddressAdapter(addresses, CommonAddressActivity.this);

        view.setAdapter(addressAdapter);

    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
