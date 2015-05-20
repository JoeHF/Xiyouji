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
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }


        return convertView;

    }

    private final class ViewHolder {
        TextView status;
    }
}
