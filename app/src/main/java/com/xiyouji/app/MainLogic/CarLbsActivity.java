package com.xiyouji.app.MainLogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Button;
//import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by houfang on 15/5/16.
 */
public class CarLbsActivity extends Activity implements OnGetGeoCoderResultListener{
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    MapView mMapView;
    BaiduMap mBaiduMap;
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    private String locationAddress = "";
    private Double longitude, latitude;
    // UI相关
    boolean isFirstLoc = true;// 是否首次定位
    private TextView[] addr;
    private int addrNum = 0;
    private Timer timer = new Timer();
    private EditText search, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.car_location);
        TextView right  = (TextView)findViewById(R.id.right);
        search = (EditText)findViewById(R.id.search);
        city = (EditText)findViewById(R.id.city);

        right.setVisibility(View.GONE);
        addr = new TextView[5];
        addr[0] = (TextView)findViewById(R.id.addr0);
        addr[1] = (TextView)findViewById(R.id.addr1);
        addr[2] = (TextView)findViewById(R.id.addr2);
        addr[3] = (TextView)findViewById(R.id.addr3);
        addr[4] = (TextView)findViewById(R.id.addr4);

        //save.setClickable(false);

        mCurrentMode = LocationMode.NORMAL;
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

        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    public void click_to_search(View v) {
        if (search.getText().toString().length() == 0 || city.getText().toString().length() == 0) {
            new AlertDialog.Builder(CarLbsActivity.this).setTitle("提示信息").setMessage("请填写城市和大致地址").show();
        }
        mSearch.geocode(new GeoCodeOption().address(search.getText().toString()).city(city.getText().toString()));
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_choose_addr(View v) {
        Log.i("addr", addr[v.getTag().toString().charAt(0) - '0'].getText().toString());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("location_addr", addr[v.getTag().toString().charAt(0) - '0'].getText().toString());
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        intent.putExtras(bundle);
        setResult(Constant.START_LBS_BACK, intent);
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(CarLbsActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        Log.i("search", geoCodeResult.getLocation().latitude + geoCodeResult.getAddress());
        latitude = geoCodeResult.getLocation().latitude;
        longitude = geoCodeResult.getLocation().longitude;
        addrNum = 0;
        LatLng ll = new LatLng(latitude, longitude);
        SearchPosition(ll);

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(CarLbsActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        try {
            Log.i("map", "here");
            if (addrNum == 0) {
                mBaiduMap.clear();
                mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_marka)));
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                        .getLocation()));
            }
            addr[addrNum].setText(result.getAddress());
            addrNum++;
            if(addrNum > 4)
                return;
            if(addrNum == 1) {
                LatLng ll = new LatLng(latitude + 0.001, longitude);
                SearchPosition(ll);
            } else if(addrNum == 2) {
                LatLng ll = new LatLng(latitude, longitude + 0.001);
                SearchPosition(ll);
            } else if(addrNum == 3) {
                LatLng ll = new LatLng(latitude - 0.001, longitude);
                SearchPosition(ll);
            } else {
                LatLng ll = new LatLng(latitude, longitude - 0.001);
                SearchPosition(ll);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

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

            if (isFirstLoc) {
                //timer.schedule(task, 1000, 1000);
                SearchPosition(ll);

                /*ll = new LatLng(latitude, longitude + 1);
                SearchPosition(ll);
                ll = new LatLng(latitude - 1, longitude);
                SearchPosition(ll);
                ll = new LatLng(latitude, longitude - 1);
                SearchPosition(ll);*/
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

    /**
     * 地址搜索
     *
     * @param latLng
     */
    public void SearchPosition(LatLng latLng) {
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(latLng));

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

}
