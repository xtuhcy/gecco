package com.geccocrawler.gecco.demo.weather;

import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.spider.JsonBean;

public class CityInfoEntity implements JsonBean {
    /**
     * parent : 天津
     * city : 天津市
     * citykey : 101030100
     * updateTime : 05:40
     */
    private String parent;
    @JSONPath("$.city")
    private String city;
    private String citykey;
    private String updateTime;

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getParent() {
        return parent;
    }

    public String getCity() {
        return city;
    }

    public String getCitykey() {
        return citykey;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}

