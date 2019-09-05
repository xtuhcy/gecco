package com.geccocrawler.gecco.demo.weather;

public class YesterdayEntity {
    /**
     * date : 02
     * ymd : 2019-09-02
     * high : 高温 33℃
     * sunrise : 05:39
     * fx : 西南风
     * week : 星期一
     * low : 低温 23℃
     * fl : <3级
     * sunset : 18:42
     * aqi : 87
     * type : 晴
     * notice : 愿你拥有比阳光明媚的心情
     */
    private String date;
    private String ymd;
    private String high;
    private String sunrise;
    private String fx;
    private String week;
    private String low;
    private String fl;
    private String sunset;
    private int aqi;
    private String type;
    private String notice;

    public void setDate(String date) {
        this.date = date;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDate() {
        return date;
    }

    public String getYmd() {
        return ymd;
    }

    public String getHigh() {
        return high;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getFx() {
        return fx;
    }

    public String getWeek() {
        return week;
    }

    public String getLow() {
        return low;
    }

    public String getFl() {
        return fl;
    }

    public String getSunset() {
        return sunset;
    }

    public int getAqi() {
        return aqi;
    }

    public String getType() {
        return type;
    }

    public String getNotice() {
        return notice;
    }
}
