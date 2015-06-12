package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pingplusplus.android.PaymentActivity;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Order;
import com.xiyouji.app.Model.Waiter;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by houfang on 2015/5/2.
 */
public class PayJudgeActivity extends Activity {
    //ui
    private TextView title, right; //Action Bar
    private CheckBox pay_weixin, pay_ali;

    private TextView carInfoValue, priceValue, washTypeValue;

    private String waiterId;
    private String orderId;
    private Waiter waiter = new Waiter();
    String money = "";

    private static final String CHANNEL_WECHAT = "wx";
    private static final String CHANNEL_ALIPAY = "alipay";
    private static final int REQUEST_CODE_PAYMENT = 1;

    private TextView xiaoerScoreValue, xiaoerNumValue, xiaoerIdValue;
    private ImageView img1, img2, img3, img4, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_judge);
        Bundle bundle = getIntent().getExtras();
        waiterId = bundle.getString("waiterId");
        orderId = bundle.getString("orderId");

        title = (TextView)findViewById(R.id.title);
        right = (TextView)findViewById(R.id.right);
        priceValue = (TextView)findViewById(R.id.price);
        carInfoValue = (TextView)findViewById(R.id.car_info);
        washTypeValue = (TextView)findViewById(R.id.washing_type);
        pay_ali = (CheckBox)findViewById(R.id.pay_ali);
        pay_weixin = (CheckBox)findViewById(R.id.pay_weixin);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);



        title.setText("支付与评价");
        right.setText("投诉");

        RequestParams requestParams = new RequestParams();
        requestParams.put("orderid", orderId);
        RestClient.post(Constant.GET_ORDER_DETAIL, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("get order detail", response.toString());
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
                        order.setType("车外清洗");
                        money = "99";
                        priceValue.setText(money);
                    } else {
                        order.setType("车内外清洗");
                        money = "999";
                        priceValue.setText(money);
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
                    carInfoValue.setText(order.getNumber() + " "
                            + order.getBrand() + order.getVersion() + " "
                            + order.getColor());
                    washTypeValue.setText(order.getType());
                    GetImgData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void GetImgData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("orderid", orderId);
        Log.i("get image", orderId);
        RestClient.post(Constant.GET_WAITER_IMAGE_AFTER, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("get image", responses.toString());
                try {
                    for (int i = 0; i < responses.length(); i++) {
                        JSONObject jsonObject = responses.getJSONObject(i);
                        String imageid = jsonObject.getString("name");
                        String url = "http://121.40.130.54/xiyouji/upload/" + imageid + ".jpg";
                        Log.i("a", url);
                        switch (i) {
                            case 0:
                                image = img1;
                                break;
                            case 1:
                                image = img2;
                                break;
                            case 2:
                                image = img3;
                                break;
                            case 3:
                                image = img4;
                                break;
                            default:
                                break;
                        }

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
                                    image.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers,
                                                  byte[] responseBody, Throwable error) {
                                error.printStackTrace();
                            }
                        });
                    }
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

    public void click_choose_payment(View v) {
        if(v.getTag().equals("weixin")) {
            pay_weixin.setChecked(true);
            pay_ali.setChecked(false);
        }
        else {
            pay_weixin.setChecked(false);
            pay_ali.setChecked(true);
        }
    }

    public void click_right(View v) {

    }

    public void click_to_pay(View v) {
        if (money.length() != 0) {
            String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
            String cleanString = money.replaceAll(replaceable, "");
            int amount = Integer.valueOf(new BigDecimal(cleanString).toString());

            String result = ""; //test
            Intent intent = new Intent();
            String packageName = this.getPackageName();
            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
            intent.setComponent(componentName);
            intent.putExtra(PaymentActivity.EXTRA_CHARGE, result);
            this.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }
        else {
            new AlertDialog.Builder(PayJudgeActivity.this).setTitle("提示信息").setMessage("请稍后正在加载数据").show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } /*else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "An invalid Credential was submitted.", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}
