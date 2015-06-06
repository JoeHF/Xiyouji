package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 2015/5/3.
 */
public class OrderOngoingAdapter extends BaseAdapter {
    private List<Order> order_ongoings;
    private Context mContext;

    public OrderOngoingAdapter(List<Order> order_ongoings, Context context) {
        this.order_ongoings = order_ongoings;
        this.mContext = context;
    }

    public void refresh(List<Order> order_ongoings) {
        this.order_ongoings = order_ongoings;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return order_ongoings.size();
    }

    @Override
    public Object getItem(int position) {
        return order_ongoings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.order_ing_list_item, null);
            holder = new ViewHolder();
            holder.status = (TextView)convertView.findViewById(R.id.status);
            holder.carType = (TextView)convertView.findViewById(R.id.carType);
            holder.location = (TextView)convertView.findViewById(R.id.location);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.status.setText(order_ongoings.get(position).getStage());
        holder.carType.setText(order_ongoings.get(position).getNumber() + " "
                + order_ongoings.get(position).getBrand() + order_ongoings.get(position).getVersion() + " "
                + order_ongoings.get(position).getColor() + " " + order_ongoings.get(position).getType());
        holder.location.setText(order_ongoings.get(position).getSitename());
        holder.time.setText(order_ongoings.get(position).getTime());
        return convertView;

    }

    private final class ViewHolder {
        TextView status;
        TextView carType;
        TextView location;
        TextView time;
    }
}