package com.xiyouji.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xiyouji.app.Constant.Constant;
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
    private String weatherHint = "";
    private String weather = "";

    //ui
    private TextView weather_hint_value, weather_value;

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
                //JSONObject firstEvent = timeline.get(0);
                //String tweetText = firstEvent.getString("text");
                for(int i = 0 ; i < weathers.length() ; i++) {
                    try {
                        JSONObject weather = (JSONObject)weathers.get(i);
                        Log.i("http:weather JSONObject", weather.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    weatherHint = ((JSONObject)weathers.get(0)).getString("xiche");
                    weather = ((JSONObject)weathers.get(0)).getString("weather");
                    weather_hint_value.setText(weatherHint);
                    weather_value.setText(weather);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Do something with the response
                //System.out.println(tweetText);
            }
        });

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        weather_hint_value = (TextView)rootView.findViewById(R.id.washing_hint_value);
        weather_value = (TextView)rootView.findViewById(R.id.weather_value);



        return rootView;
        //return inflater.inflate(R.layout.fragment_home, container, false);


    }
}
