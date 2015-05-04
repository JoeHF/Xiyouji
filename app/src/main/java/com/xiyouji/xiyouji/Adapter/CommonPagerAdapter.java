package com.xiyouji.xiyouji.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by houfang on 2015/5/2.
 */
public class CommonPagerAdapter extends PagerAdapter {
    private List<View> mListViews;

    public CommonPagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }

    public int getCount() {
        return mListViews.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
    }

}
