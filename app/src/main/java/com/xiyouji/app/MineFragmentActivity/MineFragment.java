package com.xiyouji.app.MineFragmentActivity;

/**
 * Created by houfang on 15/4/28.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Constant.Constant;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        nameValue = (TextView)rootView.findViewById(R.id.name);
        phoneValue = (TextView)rootView.findViewById(R.id.phone);
        moneyValue = (TextView)rootView.findViewById(R.id.money);
        discountValue = (TextView)rootView.findViewById(R.id.discount);

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
        return rootView;
    }
}
