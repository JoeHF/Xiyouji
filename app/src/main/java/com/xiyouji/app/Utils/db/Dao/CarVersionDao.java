package com.xiyouji.app.Utils.db.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiyouji.app.Model.CarBrand;
import com.xiyouji.app.Model.CarVersion;
import com.xiyouji.app.Utils.db.CarBrandDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/6/4.
 */
public class CarVersionDao {
    CarBrandDBHelper carBrandDBHelper = null;
    SQLiteDatabase db;

    public CarVersionDao(Context context) {
        carBrandDBHelper = new CarBrandDBHelper(context);
        db = carBrandDBHelper.getWritableDatabase();
    }

    public void insert(CarVersion carVersion) {
        String sql = "insert into carversion(versionid,brand,version) values(?,?,?)";
        db.execSQL(sql, new Object[]{carVersion.getVersionid(), carVersion.getBrand(), carVersion.getVersion()});
    }

    public void clear() {
        String sql = "delete from carversion";
        db.execSQL(sql);
    }

    public List<CarVersion> get() {
        ArrayList<CarVersion> carVersions = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM carversion", null);
        while (c.moveToNext()) {
            String brand = c.getString(c.getColumnIndex("brand"));
            String versionId = c.getString(c.getColumnIndex("versionid"));
            String version = c.getString(c.getColumnIndex("version"));
            Log.i("db", "_version=>" + version);
            CarVersion carVersion = new CarVersion();
            carVersion.setVersionid(versionId);
            carVersion.setBrand(brand);
            carVersion.setVersion(version);
            carVersions.add(carVersion);
        }
        c.close();
        return carVersions;
    }

    public List<CarVersion> getVersionByBrand(CarBrand carBrand) {
        ArrayList<CarVersion> carVersions = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM carversion WHERE brand = ?", new String[]{carBrand.getBrand()});
        while (c.moveToNext()) {
            String brand = c.getString(c.getColumnIndex("brand"));
            String versionId = c.getString(c.getColumnIndex("versionid"));
            String version = c.getString(c.getColumnIndex("version"));
            Log.i("db", "_version=>" + version);
            CarVersion carVersion = new CarVersion();
            carVersion.setVersionid(versionId);
            carVersion.setBrand(brand);
            carVersion.setVersion(version);
            carVersions.add(carVersion);
        }
        c.close();
        return carVersions;
    }
}