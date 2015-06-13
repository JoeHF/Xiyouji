package com.xiyouji.app.MineFragmentActivity;

/**
 * Created by houfang on 15/4/28.
 */
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.MainActivity;
import com.xiyouji.app.Model.Discount;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MineFragment extends Fragment
{
    private String nickname;
    private String phone;
    private String money;
    private String userId;

    private TextView nameValue, phoneValue, moneyValue, discountValue;
    private ImageView headPhoto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ((MainActivity)getActivity()).mineFragment = this;
        nameValue = (TextView)rootView.findViewById(R.id.name);
        phoneValue = (TextView)rootView.findViewById(R.id.phone);
        moneyValue = (TextView)rootView.findViewById(R.id.money);
        discountValue = (TextView)rootView.findViewById(R.id.discount);
        headPhoto = (ImageView)rootView.findViewById(R.id.head_photo);

        SharedPreferences user = getActivity().getSharedPreferences("user", 0);
        userId = user.getString("id", "0");
        phone = user.getString("phone", "0");
        money = user.getString("money", "0");
        nickname = user.getString("nickname", "0");

        nameValue.setText(nickname);
        phoneValue.setText(phone);
        moneyValue.setText(money);

        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.post(Constant.GET_DISCOUNT_LIST, requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                discountValue.setText(responses.length() + "");
            }
        });
        GetUserIcon();
        return rootView;
    }

    public void GetUserIcon() {
        Log.i("mine", "get user icon:" + userId);
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", userId);

        RestClient.get(Constant.GET_USER_ICON, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                Log.i("user head", responses.toString());
                try {
                    if(responses.length() == 0)
                        return;
                    String imageid = responses.getJSONObject(responses.length() - 1).getString("name");
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
