package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Login.LoginActivity;
import com.xiyouji.app.MainLogic.TakePictureActivity;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.BitmapTool;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by houfang on 15/6/12.
 */
public class AdviceActivity extends Activity {
    private EditText contact, content;
    private String userId;
    private ImageView picture;

    private String imageId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice);
        contact = (EditText)findViewById(R.id.contact);
        content = (EditText)findViewById(R.id.content);
        picture = (ImageView)findViewById(R.id.picture);

        SharedPreferences user = getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void click_to_advice(View v) {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);
        requestParams.put("contact", contact.getText().toString());
        requestParams.put("content", content.getText().toString());

        RestClient.post(Constant.ADD_COMPLAIN, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("add advice", response.toString());
                try {
                    String complainId = response.getString("complainid");
                    new AlertDialog.Builder(AdviceActivity.this).setTitle("提示信息").setMessage("评论成功").show();

                    RequestParams requestParams1 = new RequestParams();
                    requestParams1.put("imageid", imageId);
                    requestParams1.put("complainid", complainId);

                    RestClient.post(Constant.UPLOAD_COMPLAIN_IMAGE, requestParams1, new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.i("add advice image", response.toString());
                        }
                    });
                }
                catch (JSONException e) {
                    new AlertDialog.Builder(AdviceActivity.this).setTitle("提示信息").setMessage("评论失败").show();
                    e.printStackTrace();
                }


            }
        });

    }

    public void click_to_take_picture(View v) {
        Intent intent = new Intent();
        intent.setClass(this, TakePictureActivity.class);
        startActivityForResult(intent, Constant.START_ADVICE_PICTURE);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求来区别
        switch (requestCode) {
            case Constant.START_ADVICE_PICTURE:
                switch (resultCode) {
                    case Constant.START_ADVICE_PICTURE_BACK:
                        Bundle bundle = data.getExtras();
                        String pictureSrc = bundle.getString("picturesrc");
                        Bitmap bitmap= BitmapTool.getLoacalBitmap(pictureSrc);
                        picture.setImageBitmap(bitmap);

                        String pictureInfo = BitmapTool.bitmaptoString(bitmap);
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("icon", pictureInfo);
                        RestClient.post(Constant.UPLOAD_PIC, requestParams, new JsonHttpResponseHandler() {
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                Log.i("上传图片", response.toString());
                                try {
                                    imageId = response.getString("imageid");
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
