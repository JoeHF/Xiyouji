package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/6/1.
 */
public class CommonCarAdapter extends BaseAdapter{
    private List<CarInfo> cars;
    private Context mContext;

    public CommonCarAdapter(List<CarInfo> _cars, Context context) {
        this.cars = _cars;
        this.mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.common_car_info_listitem, null);
            holder = new ViewHolder();
            holder.car_info = (TextView)convertView.findViewById(R.id.car_info);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        CarInfo carInfo = this.cars.get(position);
        holder.car_info.setText(carInfo.getNumber() + " " + carInfo.getColor() + " " + carInfo.getBrand());
        return convertView;
    }

    private class ViewHolder {
        TextView car_info;
    }
}
