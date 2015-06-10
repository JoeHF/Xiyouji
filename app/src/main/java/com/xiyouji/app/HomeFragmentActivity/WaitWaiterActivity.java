package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Waiter;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by houfang on 15/6/10.
 */
public class WaitWaiterActivity extends Activity {
    //ui
    private TextView title, right; //Action Bar
    private CheckBox pay_weixin, pay_ali;

    private String waiterId;
    private String orderId;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    private double latitude, longitude;

    private TextView xiaoerScoreValue, xiaoerNumValue, xiaoerIdValue, xiaoerInfoValue;

    MapView mMapView;
    BaiduMap mBaiduMap;
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    boolean isFirstLoc = true;// 是否首次定位

    private int totalTime = 0;
    private RequestParams requestParams;
    private Timer timer = new Timer();
    Waiter waiter = new Waiter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.arrange_xiaoer);
        xiaoerScoreValue = (TextView)findViewById(R.id.xiaoer_score);
        xiaoerNumValue = (TextView)findViewById(R.id.xiaoer_num);
        xiaoerIdValue = (TextView)findViewById(R.id.xiaoer_id);
        xiaoerInfoValue = (TextView)findViewById(R.id.info);

        Bundle bundle = getIntent().getExtras();
        waiterId = bundle.getString("waiterId");
        orderId = bundle.getString("orderId");

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        requestParams = new RequestParams();
        requestParams.put("waiterid", waiterId);
        RestClient.post(Constant.GET_WAITER_INFO, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("waiter info", response.toString());
                try {
                    waiter.setWaiterId(response.getString("waiterid"));
                    waiter.setLatitude(response.getDouble("lat"));
                    waiter.setLongitude(response.getDouble("long"));
                    waiter.setStar(response.getString("star"));
                    waiter.setCount(response.getString("count"));
                    waiter.setCode(response.getString("code"));
                    xiaoerScoreValue.setText(waiter.getStar());
                    xiaoerNumValue.setText(waiter.getCount());
                    xiaoerIdValue.setText("小二" + waiter.getCode());
                    xiaoerInfoValue.setText("小二" + waiter.getCode() + "正在马不停蹄的赶来");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        requestParams = new RequestParams();
        requestParams.put("orderid", orderId);
        timer.schedule(task, 1000, 1000);
        // 初始化搜索模块，注册事件监听
        //mSearch = GeoCoder.newInstance();
        //mSearch.setOnGetGeoCodeResultListener(this);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    totalTime++;
                    timer.cancel();
                    int minute = totalTime / 60;
                    int second = totalTime % 60;
                    RestClient.post(Constant.GET_ORDER_DETAIL, requestParams, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.i("get order detail", response.toString());
                            try {
                                if (response.getString("stage").equals("服务中")) {
                                    xiaoerInfoValue.setText("小二" + waiter.getCode() + "正在马不停蹄的赶来");
                                    String waiterId = response.getString("waiterid");
                                }
                                else if (response.getString("stage").equals("待支付")) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("waiterId", waiterId);
                                    bundle.putString("orderId", orderId);
                                    intent.putExtras(bundle);
                                    intent.setClass(WaitWaiterActivity.this, PayJudgeActivity.class);  //test code
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in,
                                            R.anim.push_left_out	);
                                    timer.cancel();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    };

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //SearchPosition(ll);
            if (isFirstLoc) {
                isFirstLoc = false;
                //ll = new LatLng(location.getLatitude(),
                //        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public void click_to_xiaoer(View v) {
        Intent intent = new Intent();
        intent.setClass(this, XiaoerInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("waiterId", waiterId);
        intent.putExtras(bundle);
        //startActivityForResult(intent, Constant.START_CAR_INFO);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public void click_to_back(View v) {
        timer.cancel();
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
