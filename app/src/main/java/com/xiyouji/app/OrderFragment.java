package com.xiyouji.app;

/**
 * Created by houfang on 15/4/28.
 */
import android.app.AlertDialog;
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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.CommonPagerAdapter;
import com.xiyouji.app.Adapter.OrderHistoryAdapter;
import com.xiyouji.app.Adapter.OrderOngoingAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment
{
    private ViewPager pager;
    private View rootView;
    private View page1, page2;
    private List<View> pages;
    private TextView text1, text2;

    private PullToRefreshListView view1, view2;
    //data
    private List<Order> orderOngoings, orderHistorys;
    private OrderOngoingAdapter orderOngoingAdapter;
    private OrderHistoryAdapter orderHistoryAdapter;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if(rootView == null) {
            ((MainActivity)getActivity()).orderFragment = this;
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
            pager = (ViewPager) rootView.findViewById(R.id.view_pager);
            page1 = inflater.inflate(R.layout.order_ing, null);
            page2 = inflater.inflate(R.layout.order_history, null);
            view1 = (PullToRefreshListView)page1.findViewById(R.id.listView);
            view2 = (PullToRefreshListView)page2.findViewById(R.id.listView);

            view1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    GetOrderData();
                }
            });

            view2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    GetOrderData();
                }
            });

            orderOngoings = new ArrayList<>();
            orderOngoingAdapter = new OrderOngoingAdapter(orderOngoings, getActivity());

            text1 = (TextView)rootView.findViewById(R.id.text1);
            text2 = (TextView)rootView.findViewById(R.id.text2);
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(0);
                }
            });

            text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(1);
                }
            });

            initPager();
            view1.setAdapter(orderOngoingAdapter);

            orderHistorys = new ArrayList<>();
            orderHistoryAdapter = new OrderHistoryAdapter(orderHistorys, getActivity());

            view2.setAdapter(orderHistoryAdapter);


        }

        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }

        SharedPreferences user = getActivity().getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
        GetOrderData();
        return rootView;
    }

    public void GetOrderData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.get(Constant.GET_ORDER_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                view1.onRefreshComplete();
                view2.onRefreshComplete();
                new AlertDialog.Builder(getActivity()).setTitle("提示信息").setMessage("刷新失败").show();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("order list info", responses.toString());
                view1.onRefreshComplete();
                view2.onRefreshComplete();

                try {
                    orderOngoings = new ArrayList<>();
                    for(int i = 0 ; i < responses.length() ; i++) {
                        Order order = new Order();
                        JSONObject jsonObject = responses.getJSONObject(i);
                        if (jsonObject.getString("stage").equals("已关闭"))
                            continue;

                        order.setVersion(jsonObject.getString("version"));
                        order.setBrand(jsonObject.getString("brand"));
                        order.setSitename(jsonObject.getString("sitename"));
                        order.setColor(jsonObject.getString("color"));
                        order.setWaiterId(jsonObject.getString("waiterid"));
                        order.setId(jsonObject.getInt("orderid"));
                        int type = jsonObject.getInt("type");
                        if (type == 1) {
                            order.setType("车外清洗");
                        } else {
                            order.setType("车内外清洗");
                        }

                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            String time = format.format(jsonObject.getLong("createtime") * 1000);
                            order.setTime(time);
                        } catch (Exception e) {
                            order.setTime("6月4日 23点");
                            e.printStackTrace();
                        }

                        if (jsonObject.getLong("asktime") != 0) {
                            String time = format.format(jsonObject.getLong("asktime") * 1000);
                            order.setTime(time);
                        }

                        order.setStage(jsonObject.getString("stage"));
                        order.setNumber(jsonObject.getString("number"));
                        if (!jsonObject.getString("stage").equals("已完成")) {
                            orderOngoings.add(order);
                        }
                        else {
                            orderHistorys.add(order);
                        }
                    }

                    orderOngoingAdapter.refresh(orderOngoings);
                    orderHistoryAdapter.refresh(orderHistorys);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initPager() {
        pages = new ArrayList<>();
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
