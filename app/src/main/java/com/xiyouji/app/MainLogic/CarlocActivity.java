package com.xiyouji.app.MainLogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.CommonCarLocationAdapter;
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
 * Created by houfang on 2015/4/29.
 */
public class CarlocActivity extends Activity{
    private TextView location;
    private EditText remark;
    private ListView commonCarLocListView;

    private String userId;
    private Double latitude, longitude;

    private List<Address> carLocs;
    private CommonCarLocationAdapter commonCarLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        location = (TextView)findViewById(R.id.location);
        remark = (EditText)findViewById(R.id.remark);
        commonCarLocListView = (ListView)findViewById(R.id.common_car_loc_listview);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");

        carLocs = new ArrayList<Address>();
        commonCarLocationAdapter = new CommonCarLocationAdapter(carLocs, this);
        commonCarLocListView.setAdapter(commonCarLocationAdapter);

        getCommonCarLocData();
    }

    public void getCommonCarLocData() {
        Log.i("user id", userId);
        final RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);;

        RestClient.get(Constant.GET_SITE_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("car locs list info", responses.toString());
                try {
                    carLocs = new ArrayList<Address>();
                    for (int i = 0 ; i < responses.length() ; i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        Address carLoc = new Address();
                        carLoc.setSitename(jsonObject.getString("sitename"));
                        carLoc.setHint(jsonObject.getString("hint"));
                        carLoc.setLatitude(jsonObject.getDouble("lat"));
                        carLoc.setLongitude(jsonObject.getDouble("long"));
                        carLoc.setSiteId(jsonObject.getString("siteid"));
                        carLocs.add(carLoc);
                    }

                    commonCarLocationAdapter.refresh(carLocs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void clickCommonCarLoc(Address address) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("carLoc", address.getSitename());
        bundle.putString("siteId", address.getSiteId());
        intent.putExtras(bundle);
        setResult(Constant.START_CAR_LOC_BACK, intent);
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_lbs(View w)
    {
        Intent intent = new Intent();
        intent.setClass(this, CarLbsActivity.class);
        startActivityForResult(intent, Constant.START_LBS);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_to_save_location(View v) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("hint", remark.getText().toString());
        requestParams.put("sitename", location.getText().toString());
        requestParams.put("long", longitude);
        requestParams.put("lat", latitude);
        requestParams.put("userid", userId);
        RestClient.post(Constant.ADD_CAR_LOC, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("add location info", response.toString());
                try {
                    String siteId = response.getString("siteid");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("carLoc", location.getText().toString());
                    bundle.putString("siteId", siteId);
                    intent.putExtras(bundle);
                    setResult(Constant.START_CAR_LOC_BACK, intent);
                    finish();
                    overridePendingTransition(R.anim.push_right_in,
                            R.anim.push_right_out);
                }
                catch (JSONException e) {
                    new AlertDialog.Builder(CarlocActivity.this).setTitle("提示信息").setMessage("添加地址失败").show();
                    e.printStackTrace();
                }

            }
        });
    }

    //接收回传值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.START_LBS) {
            switch (resultCode) {
                //接收定位地址
                case Constant.START_LBS_BACK:
                    Bundle bundle = data.getExtras();
                    String location_addr = bundle.getString("location_addr");
                    longitude = bundle.getDouble("longitude");
                    latitude = bundle.getDouble("latitude");
                    location.setText(location_addr);
                    Log.i("location", location_addr);
                    Log.i("long, lat", longitude + " " + latitude);
                    break;
                default:
                    break;
            }
        }
    }

}