package com.xiyouji.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiyouji.app.MainLogic.CarInfoActivity;
import com.xiyouji.app.Model.CarVersion;
import com.xiyouji.app.R;

import java.util.List;

/**
 * Created by houfang on 15/6/4.
 */
public class CarVersionDialogAdapter extends BaseAdapter {
    private Context context;
    private List<CarVersion> carVersions;

    public CarVersionDialogAdapter(Context _context, List<CarVersion> _carVersions) {
        context = _context;
        carVersions = _carVersions;
    }

    @Override
    public int getCount() {
        return carVersions.size();
    }

    @Override
    public Object getItem(int position) {
        return carVersions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.choose_car_info_dialog_item, null);
            holder = new ViewHolder();
            holder.rl = (RelativeLayout)convertView.findViewById(R.id.rl);
            holder.version = (TextView)convertView.findViewById(R.id.name);
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CarInfoActivity)context).clickVersion(carVersions.get(position));
                }
            });

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        CarVersion carVersion = this.carVersions.get(position);
        holder.version.setText(carVersion.getVersion());
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout rl;
        //ImageView cancel;
        TextView version;
    }
}
