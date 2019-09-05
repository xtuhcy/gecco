package com.geccocrawler.gecco.demo.weather;

import com.alibaba.fastjson.annotation.JSONField;

public class CityData {

    /**
     * city_name : 甘肃
     * post_code : null
     * area_code : null
     * city_code :
     * ctime : null
     * pid : 0
     * id : 4
     */
    @JSONField(name="city_name")
    private String cityName;
    @JSONField(name="post_code")
    private String postCode;
    @JSONField(name="area_code")
    private String areaCode;
    @JSONField(name="city_code")
    private String cityCode;
    @JSONField(name="ctime")
    private String ctime;
    @JSONField(name="pid")
    private int pid;
    @JSONField(name="id")
    private int id;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
