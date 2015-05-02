package com.xiyouji.xiyouji.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xiyouji.xiyouji.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by houfang on 2015/5/2.
 */
public class XiaoerPhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> lstImageItem;
    public XiaoerPhotoAdapter(Context context, ArrayList<HashMap<String, Object>> _lstImageItem) {
        this.mContext = context;
        this.lstImageItem = _lstImageItem;
    }

    @Override
    public int getCount() {
        return lstImageItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstImageItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //定义一个ImageView,显示在GridView里
        ImageView imageView;
        if(convertView==null){
            //imageView=new ImageView(mContext);
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.xiaoer_photo, null);
            imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
        }else{
            imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
        }

        imageView.setImageResource((int) lstImageItem.get(position).get("ItemImage"));
        return convertView;
    }

}
