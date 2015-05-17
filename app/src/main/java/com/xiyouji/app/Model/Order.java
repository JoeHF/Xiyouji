package com.xiyouji.app.Model;

/**
 * Created by houfang on 2015/5/3.
 */
public class Order {
    private int id;
    private String date;
    private String time;
    private String carType;
    private String location;
    private String xiaoEr;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }
    public void setCarType(String carType) {
        this.carType = carType;
    }
    public String getCarType() {
        return this.carType;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return this.location;
    }
    public void setXiaoEr(String xiaoEr) {
        this.xiaoEr = xiaoEr;
    }
    public String getXiaoEr() {
        return this.xiaoEr;
    }
}
