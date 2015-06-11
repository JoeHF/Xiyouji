package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.DiscountsAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Discount;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
        title = (TextView)findViewById(R.id.title);
        title.setText("优惠劵");

        view = (ListView)findViewById(R.id.discount_list);

        discounts = new ArrayList<Discount>();
        discountsAdapter = new DiscountsAdapter(discounts, DiscountActivity.this);

        view.setAdapter(discountsAdapter);
        GetDiscountData();
    }

    public void GetDiscountData() {


        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.post(Constant.GET_DISCOUNT_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("get discount data", responses.toString());
                try {
                    discounts = new ArrayList<Discount>();
                    for (int i = 0 ; i < responses.length() ; i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        Discount discount = new Discount();
                        discount.setId(jsonObject.getInt("ticketid"));
                        discount.setPrice(jsonObject.getString("money"));
                        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            String time = format.format(jsonObject.getLong("endtime") * 1000);
                            discount.setDueDate(time);
                        }
                        catch (Exception e) {
                            discount.setDueDate("6月4日 23点");
                            e.printStackTrace();
                        }

                        try {
                            String time = format.format(jsonObject.getLong("starttime") * 1000);
                            discount.setStartDate(time);
                        }
                        catch (Exception e) {
                            discount.setStartDate("6月4日 23点");
                            e.printStackTrace();
                        }

                        //discount.setDuedate(jsonObject.getString("endtime"));
                        //discount.setStartDate(jsonObject.getString("starttime"));
                        discount.setCode(jsonObject.getString("code"));
                        discount.setUsed(jsonObject.getInt("used"));
                        discounts.add(discount);
                    }
                    discountsAdapter.refresh(discounts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
