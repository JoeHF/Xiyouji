package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.mine.MyCommonAddressAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Address;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/5/17.
 */
public class CommonAddressActivity extends Activity{
    private TextView title;
    private SwipeListView view;
    private ImageView add;
    private List<Address> addresses;
    private MyCommonAddressAdapter myCommonAddressAdapter;

    private String userId;
    private String username;
    private String password;
    private int deviceWidth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_car);
        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
        username = user.getString("username", "0");
        password = user.getString("password", "0");

        title = (TextView)findViewById(R.id.title);
        title.setText("常用地址");
        add = (ImageView)findViewById(R.id.right);
        add.setImageDrawable(getResources().getDrawable(R.drawable.dibiao));
        view = (SwipeListView)findViewById(R.id.car_list);

        addresses = new ArrayList<>();
        myCommonAddressAdapter = new MyCommonAddressAdapter(addresses, CommonAddressActivity.this, view);

        view.setAdapter(myCommonAddressAdapter);
        view.setSwipeListViewListener( new MySwipeListViewListener());

        deviceWidth = getDeviceWidth();
        reload();
        getCommonAddrData();
    }

    private int getDeviceWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private void reload() {
        view.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        view.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        view.setOffsetLeft(deviceWidth - dip2px(this, 60));
        view.setAnimationTime(0);
        view.setSwipeOpenOnLongPress(false);
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    class MySwipeListViewListener extends BaseSwipeListViewListener {

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            Toast.makeText(getApplicationContext(), addresses.get(position).getSitename(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                Log.i("common addr", "remove:" + position);
                addresses.remove(position);
                RequestParams requestParams = new RequestParams();
                requestParams.put("phone", username);
                requestParams.put("password", password);
                requestParams.put("siteid", addresses.get(position - 1).getSiteId());

                RestClient.post(Constant.DELETE_SITE, requestParams, new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("delete addr", response.toString());
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    }

                });
            }
            myCommonAddressAdapter.notifyDataSetChanged();
        }
    }

    public void getCommonAddrData() {
        Log.i("user id", userId);
        final RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);;

        RestClient.get(Constant.GET_SITE_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("car list info", responses.toString());
                try {
                    addresses = new ArrayList<Address>();
                    for (int i = 0; i < responses.length(); i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        Address address = new Address();
                        address.setSitename(jsonObject.getString("sitename"));
                        address.setLongitude(jsonObject.getDouble("long"));
                        address.setLatitude(jsonObject.getDouble("lat"));
                        address.setSiteId(jsonObject.getString("siteid"));
                        address.setHint(jsonObject.getString("hint"));
                        addresses.add(address);
                    }

                    myCommonAddressAdapter.refresh(addresses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
