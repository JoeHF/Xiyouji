package com.xiyouji.app.HomeFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xiyouji.app.Adapter.XiaoerPhotoAdapter;
import com.xiyouji.app.Constant.Constant;
import com.xiyouji.app.Model.Waiter;
import com.xiyouji.app.R;
import com.xiyouji.app.Utils.RestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by houfang on 2015/5/2.
 */
public class XiaoerDetailActivity extends Activity {
    private Waiter waiter = new Waiter();
    private TextView xiaoerScoreValue, xiaoerNumValue, xiaoerIdValue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaoer_info);
        xiaoerScoreValue = (TextView)findViewById(R.id.xiaoer_score);
        xiaoerNumValue = (TextView)findViewById(R.id.xiaoer_num);
        xiaoerIdValue = (TextView)findViewById(R.id.xiaoer_id);

        Bundle bundle = getIntent().getExtras();
        String waiterId = bundle.getString("waiterId");

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        GridView gridView = (GridView) findViewById(R.id.gridview);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.muller_icon);//添加图像资源的ID
            lstImageItem.add(map);
        }

        gridView.setAdapter(new XiaoerPhotoAdapter(this, lstImageItem));
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应

        //添加消息处理
        gridView.setOnItemClickListener(new ItemClickListener());
    }

    //当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class  ItemClickListener implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
            //在本例中arg2=arg3
            //HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
            //setTitle((String)item.get("ItemText"));
        }

    }

    public void click_to_back(View v) {
        finish();
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
