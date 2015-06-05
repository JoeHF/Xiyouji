package com.xiyouji.app.Adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.xiyouji.app.Model.Address;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/6/5.
 */
public class MyCommonAddressAdapter extends BaseAdapter {
    private List<Address> addresses;
    private Context mContext;
    private SwipeListView swipeListView;

    public MyCommonAddressAdapter(List<Address> _addresses, Context context, SwipeListView _swipeListView) {
        this.addresses = _addresses;
        this.mContext = context;
        this.swipeListView = _swipeListView;
    }

    public void refresh(List<Address> _addresses) {
        this.addresses = _addresses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.my_common_addr_listitem, null);
            holder = new ViewHolder();
            holder.ll = (LinearLayout)convertView.findViewById(R.id.ll);
            holder.addr = (TextView)convertView.findViewById(R.id.addr);
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

        Address address = this.addresses.get(position);
        holder.addr.setText(address.getSitename());
        return convertView;
    }

    private class ViewHolder {
        LinearLayout ll;
        TextView addr;
        ImageView make_sure;
        TextView mBackDelete;
    }
}
