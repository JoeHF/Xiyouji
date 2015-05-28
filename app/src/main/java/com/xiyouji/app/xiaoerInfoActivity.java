package com.xiyouji.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiyouji.app.Adapter.XiaoerPhotoAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by houfang on 2015/5/2.
 */
public class xiaoerInfoActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaoer_info);
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
