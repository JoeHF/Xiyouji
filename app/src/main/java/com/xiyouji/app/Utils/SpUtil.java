package com.xiyouji.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xiyouji.app.MyApplication;


/**
 * 有关 sharedPerference的工具类
 *
 * @author yemeng
 */
public class SpUtil {
    private static final String NAME = "Honey";
    private static SharedPreferences sp = MyApplication.application
            .getSharedPreferences(NAME, Context.MODE_PRIVATE);

    // 是否第一次进入，app引导界面
    public static boolean isFirst() {
        return sp.getBoolean("isFirst", false);
    }

    // 清除所有数据
    public static void clean() {
        sp.edit().clear().commit();
    }

    // set方法
    public static void setStringSharedPerference(String key, String value) {
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setIntSharedPerference(String key, int value) {
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setLongSharedPerference(String key, Long value) {
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setBooleanSharedPerference(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    // get方法
    public static String getStringSharedPerference(String key, String value) {
        return sp.getString(key, value);
    }

    public static int getIntSharedPerference(String key, int value) {
        return sp.getInt(key, value);
    }

    public static long getLongSharedPerference(String key, long value) {
        return sp.getLong(key, value);
    }

    public static boolean getBooleanSharedPerference(String key, boolean value) {
        return sp.getBoolean(key, value);
    }
    /* SharedPreferences 定义的内容 */
}
