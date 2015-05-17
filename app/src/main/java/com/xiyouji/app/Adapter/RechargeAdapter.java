package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.Charge;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/5/13.
 */
public class RechargeAdapter extends BaseAdapter {
    private List<Charge> chargeList;
    private Context mContext;

    public RechargeAdapter(List<Charge> _chargeList, Context context) {
        this.chargeList = _chargeList;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return chargeList.size();
    }

    @Override
    public Object getItem(int position) {
        return chargeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            if(chargeList.get(position).getTag() == false) {
                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.recharge_list_item_head, null);
                holder = new ViewHolder();
                //holder.date = (TextView)convertView.findViewById(R.id.price);
                convertView.setTag(holder);
            }
            else {
                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.recharge_list_item, null);
                holder = new ViewHolder();
                holder.price = (TextView)convertView.findViewById(R.id.price);
                convertView.setTag(holder);
            }

        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        if(chargeList.get(position).getTag() == true) {
            holder.price.setText("30元");
        }

        return convertView;
    }

    private class ViewHolder {
        TextView date;
        TextView time;
        TextView price;
    }
}
