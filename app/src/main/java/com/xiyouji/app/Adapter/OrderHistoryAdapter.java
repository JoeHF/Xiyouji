package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.Order;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 2015/5/3.
 */
public class OrderHistoryAdapter extends BaseAdapter {
    private List<Order> order_historys;
    private Context mContext;

    public OrderHistoryAdapter(List<Order> _order_historys, Context context) {
        this.order_historys = _order_historys;
        this.mContext = context;
    }

    public void refresh(List<Order> _order_historys) {
        this.order_historys = _order_historys;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return order_historys.size();
    }

    @Override
    public Object getItem(int position) {
        return order_historys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.order_history_list_item, null);
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

        //holder.status.setText(order_historys.get(position).getStage());
        holder.carType.setText(order_historys.get(position).getNumber() + " "
                + order_historys.get(position).getBrand() + order_historys.get(position).getVersion() + " "
                + order_historys.get(position).getColor() + " " + order_historys.get(position).getType());
        holder.location.setText(order_historys.get(position).getSitename());
        holder.time.setText(order_historys.get(position).getTime());

        return convertView;

    }

    private final class ViewHolder {
        TextView status;
        TextView carType;
        TextView location;
        TextView time;
    }
}
