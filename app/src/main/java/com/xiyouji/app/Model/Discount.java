package com.xiyouji.app.Model;

/**
 * Created by houfang on 15/5/13.
 */
public class Discount {
    private int id;
    private String price;
    private String dueDate;
    private String startDate;
    private String code;
    private int used;

    public void setId(int _id) {
        this.id = _id;
    }
    public int getId() {
        return this.id;
    }
    public void setPrice(String _price) {
        this.price = _price;
    }
    public String getPrice() {
        return this.price;
    }
    public void setDuedate(String _dueDate) {
        this.dueDate = _dueDate;
    }
    public String getDueDate() {
        return this.dueDate;
    }
    public void setStartDate(String _startData) {
        this.startDate = _startData;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public void setCode(String _code) {
        this.code = _code;
    }
    public String getCode() {
        return this.code;
    }
    public void setUsed(int _used) {
        this.used = _used;
    }
    public int getUsed() {
        return this.used;
    }
}
