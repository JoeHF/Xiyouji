package com.xiyouji.app.Model;

import com.xiyouji.app.MainActivity;

/**
 * Created by houfang on 15/6/1.
 */
public class CarInfo {
    private String carid;
    private String versionid;
    private String color;
    private String number;
    private String brand;

    public void setCarid(String _carid) {
        this.carid = _carid;
    }
    public String getCarid() {
        return this.carid;
    }
    public void setVersionid(String _versionid) {
        this.versionid = _versionid;
    }
    public String getVersionid() {
        return this.versionid;
    }
    public void setColor(String _color) {
        this.color = _color;
    }
    public String getColor() {
        return this.color;
    }
    public void setNumber(String _number) {
        this.number = _number;
    }
    public String getNumber() {
        return this.number;
    }
    public void setBrand(String _brand) {
        this.brand = _brand;
    }
    public String getBrand() {
        return this.brand;
    }


}
