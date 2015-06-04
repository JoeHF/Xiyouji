package com.xiyouji.app;

/**
 * Created by houfang on 15/4/28.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.CommonPagerAdapter;
import com.xiyouji.app.Adapter.OrderHistoryAdapter;
import com.xiyouji.app.Adapter.OrderOngoingAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment
{
    private ViewPager pager;
    private View rootView;
    private View page1, page2;
    private List<View> pages;
    private TextView text1, text2;
    private ListView view1, view2;

    //data
    private List<Order> orderOngoings;
    private OrderOngoingAdapter orderOngoingAdapter;
    private OrderHistoryAdapter orderHistoryAdapter;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
            pager = (ViewPager) rootView.findViewById(R.id.view_pager);
            page1 = inflater.inflate(R.layout.order_ing, null);
            page2 = inflater.inflate(R.layout.order_history, null);
            view1 = (ListView)page1.findViewById(R.id.listView);
            view2 = (ListView)page2.findViewById(R.id.listView);

            orderOngoings = new ArrayList<Order>();
            orderOngoingAdapter = new OrderOngoingAdapter(orderOngoings, getActivity());

            text1 = (TextView)rootView.findViewById(R.id.text1);
            text2 = (TextView)rootView.findViewById(R.id.text2);
            text1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             pager.setCurrentItem(0);
                                         }
                                     }
            );
            text2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             pager.setCurrentItem(1);
                                         }
                                     }
            );
            initPager();
            view1.setAdapter(orderOngoingAdapter);

            orderOngoings = new ArrayList<Order>();
            orderOngoings.add(new Order());
            orderOngoings.add(new Order());
            orderHistoryAdapter = new OrderHistoryAdapter(orderOngoings, getActivity());

            view2.setAdapter(orderHistoryAdapter);


        }

        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }

        SharedPreferences user = getActivity().getSharedPreferences("user", 0);;
        userId = user.getString("id", "0");
        GetOrderData();
        return rootView;
    }

    public void GetOrderData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.get(Constant.GET_ORDER_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("order list info", responses.toString());

                try {
                    orderOngoings = new ArrayList<Order>();
                    for(int i = 0 ; i < responses.length() ; i++) {
                        Order order = new Order();
                        JSONObject jsonObject = responses.getJSONObject(i);
                        order.setVersion(jsonObject.getString("version"));
                        order.setBrand(jsonObject.getString("brand"));
                        order.setSitename(jsonObject.getString("sitename"));
                        order.setColor(jsonObject.getString("color"));
                        int type = jsonObject.getInt("type");
                        if(type == 1) {
                            order.setType("车外清洗");
                        }
                        else {
                            order.setType("车内外清洗");
                        }

                        order.setStage(jsonObject.getString("stage"));
                        order.setNumber(jsonObject.getString("number"));
                        orderOngoings.add(order);
                    }

                    orderOngoingAdapter.refresh(orderOngoings);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initPager() {
        pages = new ArrayList<View>();
        pages.add(page1);
        pages.add(page2);

        pager.setAdapter(new CommonPagerAdapter(pages));
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new OrderPageChangeListener());
    }

    private class OrderPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0) {
                text1.setBackgroundResource(R.color.main_theme_tab_color);
                text1.setTextColor(0xffffffff);
                text2.setBackgroundResource(R.color.white);
                text2.setTextColor(0xff228B22);
            }
            else {
                text2.setBackgroundResource(R.color.main_theme_tab_color);
                text2.setTextColor(0xffffffff);
                text1.setBackgroundResource(R.color.white);
                text1.setTextColor(0xff228B22);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
