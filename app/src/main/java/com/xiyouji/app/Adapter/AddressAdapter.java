package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Model.Address;
import com.xiyouji.app.Model.Discount;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/5/17.
 */
public class AddressAdapter extends BaseAdapter {
    private List<Address> addresses;
    private Context mContext;

    public AddressAdapter(List<Address> _addresses, Context context) {
        this.addresses = _addresses;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.address_list_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView price;
        TextView dueDate;
    }
}
