package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.Model.Waiter;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by houfang on 15/6/10.
 */
public class OrderCompleteActivity extends Activity {
    //ui
    private TextView title, right, price; //Action Bar
    private CheckBox pay_weixin, pay_ali;
    private ImageView headPhoto;

    private String waiterId;
    private String orderId;
    private Waiter waiter = new Waiter();
    private Order order = new Order();

    private TextView xiaoerScoreValue, xiaoerNumValue, xiaoerIdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_judge_paid);
        Bundle bundle = getIntent().getExtras();
        waiterId = bundle.getString("waiterId");
        orderId = bundle.getString("orderId");

        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);
        price = (TextView)findViewById(R.id.price);
        xiaoerScoreValue = (TextView)findViewById(R.id.xiaoer_score);
        xiaoerNumValue = (TextView)findViewById(R.id.xiaoer_num);
        xiaoerIdValue = (TextView)findViewById(R.id.xiaoer_id);
        headPhoto = (ImageView)findViewById(R.id.head_photo);

        RequestParams requestParams = new RequestParams();
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

                    GetHeadPhoto();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        requestParams = new RequestParams();
        requestParams.put("order", orderId);
        RestClient.post(Constant.GET_WAITER_INFO, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("order info", response.toString());
                try {
                    Order order = new Order();
                    order.setVersion(response.getString("version"));
                    order.setBrand(response.getString("brand"));
                    order.setSitename(response.getString("sitename"));
                    order.setColor(response.getString("color"));
                    order.setWaiterId(response.getString("waiterid"));
                    order.setId(response.getInt("orderid"));
                    int type = response.getInt("type");
                    if (type == 1) {
                        price.setText("99");
                        order.setType("车外清洗");
                    } else {
                        price.setText("999");
                        order.setType("车内外清洗");
                    }

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        String time = format.format(response.getLong("createtime") * 1000);
                        order.setTime(time);
                    } catch (Exception e) {
                        order.setTime("6月4日 23点");
                        e.printStackTrace();
                    }

                    if (response.getLong("asktime") != 0) {
                        String time = format.format(response.getLong("asktime") * 1000);
                        order.setTime(time);
                    }

                    order.setStage(response.getString("stage"));
                    order.setNumber(response.getString("number"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void GetHeadPhoto() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("waiterid", waiterId);

        RestClient.post(Constant.GET_WAITER_ICON, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("waiter icon", response.toString());
                try {
                    String imageid = response.getString("name");
                    String url = "http://121.40.130.54/xiyouji/upload/" + imageid + ".jpg";
                    Log.i("a", url);

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                //创建工厂对象
                                BitmapFactory bitmapFactory = new BitmapFactory();
                                //工厂对象的decodeByteArray把字节转换成Bitmap对象
                                Bitmap bitmap = bitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                //设置图片
                                headPhoto.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] responseBody, Throwable error) {
                            error.printStackTrace();
                        }
                    });
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

        RestClient.post(Constant.GET_WAITER_ICON, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                try {
                    Log.i("waiter icon", responses.toString());
                    JSONObject response = responses.getJSONObject(0);
                    String imageid = response.getString("name");
                    String url = "http://121.40.130.54/xiyouji/upload/" + imageid + ".jpg";
                    Log.i("a", url);

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                //创建工厂对象
                                BitmapFactory bitmapFactory = new BitmapFactory();
                                //工厂对象的decodeByteArray把字节转换成Bitmap对象
                                Bitmap bitmap = bitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                //设置图片
                                headPhoto.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] responseBody, Throwable error) {
                            error.printStackTrace();
                        }
                    });
                }
                catch(JSONException e){
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

    public void click_right(View v) {
        Intent intent = new Intent();
        intent.setClass(this, AdviceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }
}
