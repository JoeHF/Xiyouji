package com.xiyouji.xiyouji;

/**
 * Created by houfang on 15/4/28.
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xiyouji.xiyouji.Adapter.CommonPagerAdapter;
import com.xiyouji.xiyouji.Adapter.OrderOngoingAdapter;
import com.xiyouji.xiyouji.Model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
            pager = (ViewPager) rootView.findViewById(R.id.view_pager);
            page1 = inflater.inflate(R.layout.order_ing, null);
            page2 = inflater.inflate(R.layout.order_history, null);
            view1 = (ListView)page1.findViewById(R.id.listView);

            orderOngoings = new ArrayList<Order>();
            orderOngoings.add(new Order());
            orderOngoings.add(new Order());
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
        }

        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
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
                text1.setBackgroundResource(R.color.forestgreen);
                text1.setTextColor(0xffffffff);
                text2.setBackgroundResource(R.color.white);
                text2.setTextColor(0xff228B22);
            }
            else {
                text2.setBackgroundResource(R.color.forestgreen);
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
