package com.xiyouji.app.MineFragmentActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.RechargeAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Charge;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by houfang on 15/5/13.
 */
public class RechargeRecordActivity extends Activity {
    private TextView centerTitle;
    private ListView recordsListView;

    private List<Charge> chargeList;
    private RechargeAdapter rechargeAdapter;

    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_record);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");

        centerTitle = (TextView)findViewById(R.id.title);
        recordsListView = (ListView)findViewById(R.id.recharge_list);

        chargeList = new ArrayList<>();
        rechargeAdapter = new RechargeAdapter(chargeList, RechargeRecordActivity.this);

        recordsListView.setAdapter(rechargeAdapter);
        centerTitle.setText("充值记录");

        getRechargeRecordData();

    }

    public void getRechargeRecordData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.post(Constant.GET_RECHARGE_RECORD_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                GregorianCalendar g=new GregorianCalendar();
                Log.i("get recharge list", responses.toString());
                try {
                    chargeList.clear();
                    int lastMonth = -1;
                    if(responses.length() > 0) {
                        Charge chargeHead = new Charge();
                        chargeHead.setTag(false);
                        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        JSONObject jsonObject = responses.getJSONObject(0);
                        String time = format.format(jsonObject.getLong("time") * 1000);
                        try {
                            Date date = format.parse(time);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            chargeHead.setDate(cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月");
                            lastMonth = cal.get(Calendar.MONTH) + 1;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        chargeList.add(chargeHead);
                    }

                    for (int i = 0 ; i < responses.length() ; i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        Charge charge = new Charge();
                        charge.setPrice(jsonObject.getString("money") + "元");
                        charge.setId(jsonObject.getInt("rechargeid"));
                        charge.setPayway(jsonObject.getInt("payway"));
                        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            String time = format.format(jsonObject.getLong("time") * 1000);
                            charge.setTime(time);
                            Date date = format.parse(time);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            if(cal.get(Calendar.MONTH) + 1 != lastMonth) {
                                lastMonth = cal.get(Calendar.MONTH) + 1;
                                Charge chargeHead = new Charge();
                                chargeHead.setTag(false);
                                chargeHead.setDate(cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月");
                                chargeList.add(chargeHead);
                            }
                        }
                        catch (Exception e) {
                            charge.setTime("6月4日 23点");
                            e.printStackTrace();
                        }

                        chargeList.add(charge);
                    }

                    rechargeAdapter.refresh(chargeList);

                }
                catch (JSONException e) {
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
