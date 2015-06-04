package com.xiyouji.app.Model;

/**
 * Created by houfang on 2015/5/3.
 */
public class Order extends CarVersion {
    private int id;
    private String date;
    private String color;
    private String time;
    private String number;
    private String type;
    private String sitename;
    private String xiaoEr;
    private String stage;

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
    public void setColor(String _color) {
        this.color = _color;
    }
    public String getColor() {
        return this.color;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }
    public void setNumber(String _number) {
        this.number = _number;
    }
    public String getNumber() {
        return this.number;
    }
    public void setType(String carType) {
        this.type = carType;
    }
    public String getType() {
        return this.type;
    }
    public void setSitename(String location) {
        this.sitename = location;
    }
    public String getSitename() {
        return this.sitename;
    }
    public void setXiaoEr(String xiaoEr) {
        this.xiaoEr = xiaoEr;
    }
    public String getXiaoEr() {
        return this.xiaoEr;
    }
    public void setStage(String _stage) {
        this.stage = _stage;
    }
    public String getStage() {
        return this.stage;
    }
}
