package com.xiyouji.app.HomeFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * Created by houfang on 15/4/26.
 *
 */
public class HomeFragment extends Fragment {
    private String weather_hint_value = "";
    private String weather_value = "";
    private String date_value = "";
    private String temperature_value = "";


    //ui
    private TextView weather_hint, weather, date, temperature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RestClient.get(Constant.GET_WEATHER_BY_CITY + "?city=杭州", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("http:weather JSONObject", response.toString());
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray weathers) {
                // Pull out the first event on the public timeline
                for(int i = 0 ; i < weathers.length() ; i++) {
                    try {
                        JSONObject weather = (JSONObject)weathers.get(i);
                        Log.i("http:weather JSONObject", weather.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    weather_hint_value = ((JSONObject)weathers.get(0)).getString("xiche");
                    weather_value = ((JSONObject)weathers.get(0)).getString("weather");
                    date_value = ((JSONObject)weathers.get(0)).getString("date");
                    temperature_value = ((JSONObject)weathers.get(0)).getString("temp");
                    weather_hint.setText(weather_hint_value);
                    weather.setText(weather_value);
                    date.setText(date_value);
                    temperature.setText(temperature_value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        weather_hint = (TextView)rootView.findViewById(R.id.washing_hint_value);
        weather = (TextView)rootView.findViewById(R.id.weather_value);
        date = (TextView)rootView.findViewById(R.id.date);
        temperature = (TextView)rootView.findViewById(R.id.temperature);


        return rootView;
        //return inflater.inflate(R.layout.fragment_home, container, false);


    }
}
