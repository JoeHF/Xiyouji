package com.xiyouji.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.xiyouji.app.MineFragmentActivity.CommonAddressActivity;
import com.xiyouji.app.MineFragmentActivity.DiscountActivity;
import com.xiyouji.app.MineFragmentActivity.RechargeActivity;
import com.xiyouji.app.Model.Address;


public class MainActivity extends FragmentActivity implements OnGestureListener
{

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            Log.i("lbs", "action: " + s);

            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Log.i("lbs", "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Log.i("lbs", "网络出错");
            }
        }
    }

    private SDKReceiver mReceiver;

    public static Fragment[] fragments;
    public static LinearLayout[] linearLayouts;
    public static CheckBox[] checkBoxes;
    /**定义手势检测实例*/
    public static GestureDetector detector;
    /**做标签，记录当前是哪个fragment*/
    public int MARK=0;
    /**定义手势两点之间的最小距离*/
    final int DISTANT=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //分别实例化和初始化fragement、lineatlayout、textview
        setfragment();
        setlinearLayouts();
        setCheckBoxes();
        //创建手势检测器
        detector=new GestureDetector(this, this, null);

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**初始化fragment*/
    public void setfragment()
    {
        fragments=new Fragment[3];
        fragments[0]=getSupportFragmentManager().findFragmentById(R.id.fragment1);
        fragments[1]=getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragments[2]=getSupportFragmentManager().findFragmentById(R.id.fragment3);
        getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
            .show(fragments[0]).commit();
    }
    /**初始化linerlayout*/
    public void setlinearLayouts()
    {
        linearLayouts=new LinearLayout[3];
        linearLayouts[0]=(LinearLayout)findViewById(R.id.lay1);
        linearLayouts[1]=(LinearLayout)findViewById(R.id.lay2);
        linearLayouts[2]=(LinearLayout)findViewById(R.id.lay3);
        //linearLayouts[0].setBackgroundResource(R.drawable.lay_select_bg);
    }
    /**初始化textview*/
    public void setCheckBoxes()
    {

        checkBoxes=new CheckBox[3];
        checkBoxes[0] = (CheckBox)findViewById(R.id.hometab);
        checkBoxes[1] = (CheckBox)findViewById(R.id.ordertab);
        checkBoxes[2] = (CheckBox)findViewById(R.id.minetab);
        checkBoxes[0].setChecked(true);
    }

    /**点击底部linerlayout实现切换fragment的效果*/
    public void LayoutOnclick(View v)
    {
        resetlaybg();//每次点击都重置linearLayouts的背景、textViews字体颜色
        switch (v.getId()) {
            case R.id.lay1:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[0]).commit();
                //linearLayouts[0].setBackgroundResource(R.drawable.lay_select_bg);
                checkBoxes[0].setChecked(true);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(false);
                //textViews[0].setTextColor(getResources().getColor(R.color.lightseagreen));
                MARK=0;
                break;
            case R.id.lay2:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[1]).commit();
                //linearLayouts[1].setBackgroundResource(R.drawable.lay_select_bg);
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(true);
                checkBoxes[2].setChecked(false);
                //textViews[1].setTextColor(getResources().getColor(R.color.lightseagreen));
                MARK=1;
                break;
            case R.id.lay3:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[2]).commit();
                //linearLayouts[2].setBackgroundResource(R.drawable.lay_select_bg);
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(true);
                //textViews[2].setTextColor(getResources().getColor(R.color.lightseagreen));
                MARK=2;
                break;
            default:
                break;
            }
    }

    public void clickcheckbox(View v) {
        switch (v.getId()) {
            case R.id.hometab:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                        .show(fragments[0]).commit();
                checkBoxes[0].setChecked(true);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(false);
                MARK = 0;
                break;
            case R.id.ordertab:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                        .show(fragments[1]).commit();
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(true);
                checkBoxes[2].setChecked(false);
                MARK = 1;
                break;
            case R.id.minetab:
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                        .show(fragments[2]).commit();
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(true);
                MARK = 2;
                break;
            default:
                break;
        }
    }
    /**重置linearLayouts、textViews*/
    public void resetlaybg()
    {
        checkBoxes[0].setChecked(false);
        checkBoxes[1].setChecked(false);
        checkBoxes[2].setChecked(false);
        /*
        for(int i=0;i<3;i++)
        {
            linearLayouts[i].setBackgroundResource(R.drawable.tabfootbg);
            textViews[i].setTextColor(getResources().getColor(R.color.black));
        }*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //将该Activity上触碰事件交给GestureDetector处理
        return detector.onTouchEvent(event);
    }

    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    /**滑动切换效果的实现*/

    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
    // TODO Auto-generated method stub
        resetlaybg();
        //当是Fragment0的时候
        if(MARK==0)
        {
            if(arg1.getX()<arg0.getX()+DISTANT)
            {
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[1]).commit();
                //linearLayouts[1].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[1].setTextColor(getResources().getColor(R.color.lightseagreen));
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(true);
                checkBoxes[2].setChecked(false);
                MARK=1;
            }
            else
            {
                checkBoxes[0].setChecked(true);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(false);
                //linearLayouts[0].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[0].setTextColor(getResources().getColor(R.color.lightseagreen));
            }
        }
        //当是Fragment1的时候
        else if (MARK==1)
        {
            if(arg1.getX() < arg0.getX()+DISTANT)
            {
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[2]).commit();
                //linearLayouts[2].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[2].setTextColor(getResources().getColor(R.color.lightseagreen));
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(true);
                MARK=2;
            }
            else if(arg0.getX() < arg1.getX()+DISTANT)
            {
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[0]).commit();
                checkBoxes[0].setChecked(true);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(false);
                //linearLayouts[0].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[0].setTextColor(getResources().getColor(R.color.lightseagreen));
                MARK=0;
            }
            else
            {
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(true);
                checkBoxes[2].setChecked(false);
                //linearLayouts[1].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[1].setTextColor(getResources().getColor(R.color.lightseagreen));
            }
        }
        //当是Fragment2的时候
        else if(MARK==2)
        {
            if(arg0.getX() < arg1.getX()+DISTANT)
            {
                getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2])
                    .show(fragments[1]).commit();
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(true);
                checkBoxes[2].setChecked(false);
                //linearLayouts[1].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[1].setTextColor(getResources().getColor(R.color.lightseagreen));
                MARK=1;
            }
            else
            {
                //linearLayouts[2].setBackgroundResource(R.drawable.lay_select_bg);
                //textViews[2].setTextColor(getResources().getColor(R.color.lightseagreen));
                checkBoxes[0].setChecked(false);
                checkBoxes[1].setChecked(false);
                checkBoxes[2].setChecked(true);
            }
        }
        return false;
    }

    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
    }

    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * click listener
     */
    public void click_to_wash(View v) {
        Intent intent = new Intent();
        intent.setClass(this, WantWashingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_to_recharge(View v) {
        Intent intent = new Intent();
        intent.setClass(this, RechargeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_to_discount(View v) {
        Intent intent = new Intent();
        intent.setClass(this, DiscountActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    public void click_to_address(View v) {
        Intent intent = new Intent();
        intent.setClass(this, CommonAddressActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out	);
    }

    //todo 退出登录
    public void click_to_logout(View v){

    }

}


