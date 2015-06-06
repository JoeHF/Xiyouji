package com.xiyouji.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.Discount;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/5/13.
 */
public class DiscountsAdapter extends BaseAdapter {
    private List<Discount> discountLists;
    private Context mContext;

    public DiscountsAdapter(List<Discount> _discountLists, Context context) {
        this.discountLists = _discountLists;
        this.mContext = context;
    }

    public void refresh(List<Discount> _discountLists) {
        this.discountLists = _discountLists;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return discountLists.size();
    }

    @Override
    public Object getItem(int position) {
        return discountLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.discount_list_item, null);
            holder = new ViewHolder();
            holder.price = (TextView)convertView.findViewById(R.id.price);
            holder.dueDate = (TextView)convertView.findViewById(R.id.due_date);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.price.setText("￥" + discountLists.get(position).getPrice());
        holder.dueDate.setText(discountLists.get(position).getDueDate());
        return convertView;
    }

    private class ViewHolder {
        TextView price;
        TextView dueDate;
    }
}
