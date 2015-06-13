package com.xiyouji.app.MineFragmentActivity;

/**
 * Created by houfang on 15/6/13.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.MineFragmentActivity.MineFragment;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.BitmapTool;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UploadIconActivity extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    private String src=null;
    ImageView imageView;
    Bitmap bitmap;
    String userid;
    String imageid;
    //String orderid;
    int mark=-1;
    String waiterid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadicon);

        SharedPreferences mySharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        src=mySharedPreferences.getString("src","0");
        imageView = (ImageView) findViewById(R.id.imgView);
        bitmap = BitmapTool.getLoacalBitmap(src);
        imageView.setImageBitmap(bitmap);
    }

    public void click_to_picture(View v)
    {
        String imagename = BitmapTool.bitmaptoString(bitmap);
        RequestParams requestParams = new RequestParams();
        requestParams.put("icon", imagename);

        SharedPreferences mySharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        userid = mySharedPreferences.getString("id", "0");
        RestClient.post(Constant.UPLOAD_PIC, requestParams, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try {
                    String imageid = response.getString("imageid");
                    Log.i("imageid",imageid);
                    Log.i("userid", userid);
                    RequestParams requestParams2 = new RequestParams();
                    requestParams2.put("imageid", imageid);
                    requestParams2.put("userid", userid);

                    RestClient.post(Constant.UPLOAD_USER_ICON, requestParams2, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.i("upload user icon", response.toString());
                            Intent intent = new Intent();
                            setResult(Constant.START_LOAD_IMAGE_BACK, intent);
                            finish();
                        }
                    });
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

