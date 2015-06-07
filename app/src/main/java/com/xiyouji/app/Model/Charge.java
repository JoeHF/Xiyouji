package com.xiyouji.app.Model;

/**
 * Created by houfang on 15/5/13.
 */
public class Charge {
    private int id;
    private String date;
    private String time;
    private String price;
    private int payway;
    private Boolean tag = true;

    public void setId(int _id) {
        this.id = _id;
    }
    public int getId() {
        return this.id;
    }
    public void setDate(String _date) {
        this.date = _date;
    }
    public String getDate() {
        return this.date;
    }
    public void setTime(String _time) {
        this.time = _time;
    }
    public String getTime() {
        return this.time;
    }
    public void setPrice(String _price) {
        this.price = _price;
    }
    public String getPrice() {
        return this.price;
    }
    public void setTag(Boolean _tag) {
        this.tag = _tag;
    }
    public Boolean getTag() {
        return this.tag;
    }
    public void setPayway(int _payway) {
        this.payway = _payway;
    }
    public int getPayway() {
        return this.payway;
    }
}
