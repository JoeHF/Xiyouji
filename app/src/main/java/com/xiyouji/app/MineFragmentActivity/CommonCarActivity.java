package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.AddressAdapter;
import com.xiyouji.app.Adapter.mine.MyCommonCarAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Address;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/6/5.
 */
public class CommonCarActivity extends Activity {
    private TextView title;
    private SwipeListView view;
    private ImageView add;
    private List<CarInfo> carInfos;
    private MyCommonCarAdapter myCommonCarAdapter;

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
        title.setText("常用车辆");
        add = (ImageView)findViewById(R.id.right);
        add.setImageDrawable(getResources().getDrawable(R.drawable.dibiao));
        view = (SwipeListView)findViewById(R.id.car_list);

        carInfos = new ArrayList<>();
        myCommonCarAdapter = new MyCommonCarAdapter(carInfos, CommonCarActivity.this, view);

        view.setAdapter(myCommonCarAdapter);
        view.setSwipeListViewListener( new MySwipeListViewListener());

        deviceWidth = getDeviceWidth();
        reload();
        getCommonCarData();
    }

    private int getDeviceWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private void reload() {
        view.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        view.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
//                mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        view.setOffsetLeft(deviceWidth - dip2px(this, 60));

//                mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
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
            Toast.makeText(getApplicationContext(), carInfos.get(position).getBrand(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                Log.i("common car", "remove:" + position);
                carInfos.remove(position);
                RequestParams requestParams = new RequestParams();
                requestParams.put("phone", username);
                requestParams.put("password", password);
                requestParams.put("carid", carInfos.get(position - 1).getCarid());

                RestClient.post(Constant.DELETE_CAR_INFO, requestParams, new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("delete car", response.toString());
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    }

                });
            }
            myCommonCarAdapter.notifyDataSetChanged();
        }
    }

    public void getCommonCarData() {
        Log.i("user id", userId);
        final RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);;

        RestClient.get(Constant.GET_CAR_LIST_INFO, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("car list info", responses.toString());
                try {
                    carInfos = new ArrayList<CarInfo>();
                    for (int i = 0; i < responses.length(); i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        CarInfo carInfo = new CarInfo();
                        carInfo.setCarid(jsonObject.getString("carid"));
                        carInfo.setColor(jsonObject.getString("color"));
                        carInfo.setBrand(jsonObject.getString("brand"));
                        carInfo.setNumber(jsonObject.getString("number"));
                        carInfos.add(carInfo);
                    }

                    myCommonCarAdapter.refresh(carInfos);
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
