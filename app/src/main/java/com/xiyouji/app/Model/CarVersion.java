package com.xiyouji.app.Model;

/**
 * Created by houfang on 15/6/4.
 */
public class CarVersion {
    private String versionid;
    private String brand;
    private String version;
    public void setVersionid(String _versionid) {
        this.versionid = _versionid;
    }
    public String getVersionid() {
        return this.versionid;
    }
    public void setBrand(String _brand) {
        this.brand = _brand;
    }
    public String getBrand() {
        return this.brand;
    }
    public void setVersion(String _version) {
        this.version = _version;
    }
    public String getVersion() {
        return this.version;
    }
}
