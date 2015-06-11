package com.xiyouji.app.Model;

/**
 * Created by houfang on 15/6/10.
 */
public class Waiter {
    private String star;
    private String count;
    private String income;
    private String waiterId;
    private String code;
    private double longitude;
    private double latitude;

    public void setStar(String _star) {
        this.star = _star;
    }
    public String getStar() {
        return this.star;
    }
    public void setCount(String _count) {
        this.count = _count;
    }
    public String getCount() {
        return this.count;
    }
    public void setIncome(String _income) {
        this.income = _income;
    }
    public String getIncome() {
        return this.income;
    }
    public void setWaiterId(String _waiterId) {
        this.waiterId = _waiterId;
    }
    public String getWaiterId() {
        return this.waiterId;
    }
    public void setCode(String _code) {
        this.code = _code;
    }
    public String getCode() {
        return this.code;
    }
    public void setLongitude(Double _longitude) {
        this.longitude = _longitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLatitude(Double _latitude) {
        this.latitude = _latitude;
    }
    public double getLatitude() {
        return this.latitude;
    }

}
