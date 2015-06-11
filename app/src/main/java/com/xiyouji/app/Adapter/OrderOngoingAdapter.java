package com.xiyouji.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.HomeFragmentActivity.OrderCompleteActivity;
import com.xiyouji.app.HomeFragmentActivity.PayJudgeActivity;
import com.xiyouji.app.MainActivity;
import com.xiyouji.app.MainLogic.WaitWaiterActivity;
import com.xiyouji.app.MainLogic.WaitWashingActivity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.order_ing_list_item, null);
            holder = new ViewHolder();
            holder.status = (TextView)convertView.findViewById(R.id.status);
            holder.carType = (TextView)convertView.findViewById(R.id.carType);
            holder.location = (TextView)convertView.findViewById(R.id.location);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            holder.detail = (TextView)convertView.findViewById(R.id.detail);
            holder.listener = new MyClickListener(position);
            convertView.setOnClickListener(holder.listener);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.listener.setPosition(position);
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
        TextView detail;
        MyClickListener listener;
    }

    private class MyClickListener implements View.OnClickListener {
        private int position;

        public MyClickListener(int _position) {
            this.position = _position;
        }
        @Override
        public void onClick(View v) {
            Log.i("order detail", "click");
            Order order = order_ongoings.get(position);
            Log.i("order detail", position + ":" + order.getStage());
            if (order.getStage().equals("待接单")) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", order.getId() + "");
                bundle.putString("waiterId", order.getWaiterId());
                intent.putExtras(bundle);
                intent.setClass(mContext, WaitWashingActivity.class);
                ((MainActivity)mContext).startActivityForResult(intent, Constant.START_WAIT_WASHING);
            }
            else if (order.getStage().equals("服务中") || order.getStage().equals("已安排小二")) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", order.getId() + "");
                bundle.putString("waiterId", order.getWaiterId());
                intent.putExtras(bundle);
                intent.setClass(mContext, WaitWaiterActivity.class);
                ((MainActivity)mContext).startActivityForResult(intent, Constant.START_WAIT_WAITER);
            }
            else if (order.getStage().equals("待支付")) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", order.getId() + "");
                bundle.putString("waiterId", order.getWaiterId());
                intent.putExtras(bundle);
                intent.setClass(mContext, PayJudgeActivity.class);
                mContext.startActivity(intent);
            }
            else {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("orderId", order.getId() + "");
                bundle.putString("waiterId", order.getWaiterId());
                intent.putExtras(bundle);
                intent.setClass(mContext, OrderCompleteActivity.class);
                mContext.startActivity(intent);
            }
        }

        public void setPosition(int _position) {
            this.position = _position;
        }
    }
}
