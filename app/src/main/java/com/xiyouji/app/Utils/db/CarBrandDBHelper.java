package com.xiyouji.app.Utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiyouji.app.Constant.Constant;

/**
 * Created by houfang on 15/6/3.
 */
public class CarBrandDBHelper extends SQLiteOpenHelper {

    public CarBrandDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }

    public CarBrandDBHelper(Context context) {
        this(context, Constant.DATABASE_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建车辆品牌数据库表
        String sql = "create table carbrand(" +
                "id integer primary key autoincrement," +
                "brand varchar(20))";
        db.execSQL(sql);

        //创建车辆品牌型号数据库表
        sql = "create table carversion(" +
                "id integer primary key autoincrement," +
                "versionid varchar(20)," +
                "brand varchar(20)," +
                "version varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists carbrand";
        db.execSQL(sql);
        sql = "create table carbrand(" +
                "id integer primary key autoincrement," +
                "brand varchar(20))";
        db.execSQL(sql);

        sql = "drop table if exists carversion";
        db.execSQL(sql);
        sql = "create table carversion(" +
                "id integer primary key autoincrement," +
                "versionid varchar(20)," +
                "brand varchar(20)," +
                "version varchar(20))";
        db.execSQL(sql);
    }


}
