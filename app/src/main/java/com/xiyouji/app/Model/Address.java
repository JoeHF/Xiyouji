package com.xiyouji.app.Model;

/**
 * Created by houfang on 15/5/17.
 */
public class Address {
    private String siteId;
    private String hint;
    private String sitename;
    private Double longitude;
    private Double latitude;

    public void setSitename(String _addr) {
        this.sitename = _addr;
    }
    public String getSitename() {
        return this.sitename;
    }
    public void setSiteId(String _siteId) {
        this.siteId = _siteId;
    }
    public String getSiteId() {
        return this.siteId;
    }
    public void setHint(String _hint) {
        this.hint = _hint;
    }
    public String getHint() {
        return this.hint;
    }
    public void setLatitude(Double _latitude) {
        this.latitude = _latitude;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLongitude(Double _longitude) {
        this.longitude = _longitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }

}
