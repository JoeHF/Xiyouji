package com.xiyouji.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.CommonCarAdapter;
import com.xiyouji.app.Adapter.OrderOngoingAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.CarInfo;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by houfang on 2015/4/29.
 */
public class CarInfoActivity extends Activity{
    private Dialog dialog;
    private TextView provinceValue, brandValue, colorValue;
    private EditText number;
    private ListView commonCarListView;


    private String[] provinces = {"京", "津", "冀", "晋", "辽", "吉", "黑", "沪", "苏", "浙", "皖"};
    private String [] brands = {"奔驰", "宝马", "奥迪", "凯迪拉克", "大众", "雪佛兰", "别克"};
    private String [] colors = {"红", "黄", "蓝", "绿", "灰", "黑", "白", "紫", "香槟", "橘黄"};
    private List<Map<String, Object>> data_list;

    private List<CarInfo> carInfos;
    private CommonCarAdapter commonCarAdapter;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_car);
        provinceValue = (TextView)findViewById(R.id.province);
        brandValue = (TextView)findViewById(R.id.brand);
        colorValue = (TextView)findViewById(R.id.color);
        number = (EditText)findViewById(R.id.number);
        commonCarListView = (ListView)findViewById(R.id.common_car_listview);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");

        carInfos = new ArrayList<CarInfo>();
        commonCarAdapter = new CommonCarAdapter(carInfos, this);
        commonCarListView.setAdapter(commonCarAdapter);

        getCommonCarData();
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
                    for (int i = 0 ; i < responses.length() ; i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        CarInfo carInfo = new CarInfo();
                        carInfo.setCarid(jsonObject.getString("carid"));
                        carInfo.setColor(jsonObject.getString("color"));
                        carInfo.setBrand(jsonObject.getString("brand"));
                        carInfo.setNumber(jsonObject.getString("number"));
                        carInfos.add(carInfo);
                    }

                    commonCarAdapter.refresh(carInfos);
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

    public void click_to_province_choose(View v)
    {
        View view = View.inflate(this,R.layout.choose_car_province, null);
        GridView gridView = (GridView)view.findViewById(R.id.province);
        data_list = new ArrayList<Map<String, Object>>();
        getProvinceData();

        String [] from = {"province"};
        int [] to = {R.id.province};

        SimpleAdapter sim_adapter = new SimpleAdapter(this, data_list, R.layout.province_item, from, to);
        gridView.setAdapter(sim_adapter);

        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();

    }

    public List<Map<String, Object>> getProvinceData(){
        for(int i = 0 ; i < provinces.length ; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("province", provinces[i]);
            data_list.add(map);
        }

        return data_list;
    }

    public void click_to_province(View v) {
        Log.i("province", (String) ((TextView)v).getText());
        provinceValue.setText(((TextView)v).getText());
        dialog.dismiss();
    }

    public void click_to_brand_choose(View v) {
        View view = View.inflate(this,R.layout.choose_car_brand, null);
        GridView gridView = (GridView)view.findViewById(R.id.brand);
        data_list = new ArrayList<Map<String, Object>>();
        getBrandData();

        String [] from = {"brand"};
        int [] to = {R.id.brand};

        SimpleAdapter sim_adapter = new SimpleAdapter(this, data_list, R.layout.brand_item, from, to);
        gridView.setAdapter(sim_adapter);

        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public List<Map<String, Object>> getBrandData(){
        for(int i = 0 ; i < brands.length ; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("brand", brands[i]);
            data_list.add(map);
        }

        return data_list;
    }

    public void click_to_brand(View v) {
        Log.i("brand", (String) ((TextView)v).getText());
        brandValue.setText(((TextView)v).getText());
        dialog.dismiss();
    }

    public void click_to_color_choose(View v) {
        View view = View.inflate(this,R.layout.choose_car_color, null);
        GridView gridView = (GridView)view.findViewById(R.id.color);
        data_list = new ArrayList<Map<String, Object>>();
        getColorData();

        String [] from = {"color"};
        int [] to = {R.id.color};

        SimpleAdapter sim_adapter = new SimpleAdapter(this, data_list, R.layout.color_item, from, to);
        gridView.setAdapter(sim_adapter);

        dialog = new Dialog(this,R.style.MyDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public List<Map<String, Object>> getColorData(){
        for(int i = 0 ; i < colors.length ; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("color", colors[i]);
            data_list.add(map);
        }

        return data_list;
    }

    public void click_to_color(View v) {
        Log.i("color", (String) ((TextView)v).getText());
        colorValue.setText(((TextView)v).getText());
        dialog.dismiss();
    }

    public void click_to_icon(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, IconChooseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    //todo 保存车辆信息
    public void click_to_save_information(View v) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("number", provinceValue.getText().toString() + number.getText().toString());
        requestParams.put("versionid", brandValue.getText().toString());
        requestParams.put("color", colorValue.getText().toString());
        requestParams.put("userid", userId);

        RestClient.post(Constant.ADD_CAR_INFO, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("add car info", response.toString());

            }
        });

    }

    public void click_to_cancel(View v) {
        dialog.dismiss();
    }
}
