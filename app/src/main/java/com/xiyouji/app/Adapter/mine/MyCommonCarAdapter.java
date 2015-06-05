package com.xiyouji.app.Adapter.mine;

/**
 * Created by houfang on 15/6/5.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/6/1.
 */

public class MyCommonCarAdapter extends BaseAdapter {
    private List<CarInfo> cars;
    private Context mContext;
    private SwipeListView swipeListView;

    public MyCommonCarAdapter(List<CarInfo> _cars, Context context, SwipeListView _swipeListView) {
        this.cars = _cars;
        this.mContext = context;
        this.swipeListView = _swipeListView;
    }

    public void refresh(List<CarInfo> _cars) {
        this.cars = _cars;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.my_common_car_info_listitem, null);
            holder = new ViewHolder();
            holder.ll = (LinearLayout)convertView.findViewById(R.id.ll);
            holder.car_info = (TextView)convertView.findViewById(R.id.car_info);
            holder.make_sure = (ImageView)convertView.findViewById(R.id.make_sure);
            holder.mBackDelete = (TextView) convertView.findViewById(R.id.delete);
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.mBackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeListView.closeAnimate(position);
                swipeListView.dismiss(position);
            }
        });

        CarInfo carInfo = this.cars.get(position);
        holder.car_info.setText(carInfo.getNumber() + " " + carInfo.getColor() + " " + carInfo.getBrand());
        return convertView;
    }

    private class ViewHolder {
        LinearLayout ll;
        TextView car_info;
        ImageView make_sure;
        TextView mBackDelete;
    }

}


