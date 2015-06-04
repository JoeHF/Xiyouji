package com.xiyouji.app.Utils.db.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiyouji.app.Model.CarBrand;
import com.xiyouji.app.Utils.db.CarBrandDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houfang on 15/6/4.
 */
public class CarBrandDao {
    CarBrandDBHelper carBrandDBHelper = null;
    SQLiteDatabase db;

    public CarBrandDao(Context context) {
        carBrandDBHelper = new CarBrandDBHelper(context);
        db = carBrandDBHelper.getWritableDatabase();
    }

    public void insert(CarBrand carBrand) {
        String sql = "insert into carbrand(brand) values(?)";
        db.execSQL(sql, new Object[]{carBrand.getBrand()});
    }

    public void clear() {
        String sql = "delete from carbrand";
        db.execSQL(sql);
    }

    public List<CarBrand> get() {
        ArrayList<CarBrand> carBrands = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM carbrand", null);
        while (c.moveToNext()) {
            String brand = c.getString(c.getColumnIndex("brand"));
            Log.i("db", "_brand=>" + brand);
            CarBrand carBrand = new CarBrand();
            carBrand.setBrand(brand);
            carBrands.add(carBrand);
        }
        c.close();
        return carBrands;
    }
}
