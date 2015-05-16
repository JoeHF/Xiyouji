package com.xiyouji.xiyouji.Model;

/**
 * Created by houfang on 15/5/13.
 */
public class Discount {
    private int id;
    private String price;
    private String dueDate;

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
    public void setDuedate(String _dueData) {
        this.dueDate = dueDate;
    }
    public String getDueDate() {
        return this.dueDate;
    }
}
