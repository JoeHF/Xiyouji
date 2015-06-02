package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiyouji.app.CarInfoActivity;
import com.xiyouji.app.CarlocActivity;
import com.xiyouji.app.Model.Address;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/6/2.
 */
public class CommonCarLocationAdapter extends BaseAdapter {
    private List<Address> carLocs;
    private Context mContext;

    public CommonCarLocationAdapter(List<Address> _carLocs, Context context) {
        this.carLocs = _carLocs;
        this.mContext = context;
    }

    public void refresh(List<Address> _carLocs) {
        this.carLocs = _carLocs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return carLocs.size();
    }

    @Override
    public Object getItem(int position) {
        return carLocs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.common_car_loc_listitem, null);
            holder = new ViewHolder();
            holder.address = (TextView)convertView.findViewById(R.id.address);
            holder.make_sure = (ImageView)convertView.findViewById(R.id.make_sure);
            holder.make_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CarlocActivity)mContext).clickCommonCarLoc(carLocs.get(position));
                }
            });

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Address address = this.carLocs.get(position);
        holder.address.setText(address.getAddr());
        return convertView;
    }

    private class ViewHolder {
        TextView address;
        ImageView make_sure;
    }
}
