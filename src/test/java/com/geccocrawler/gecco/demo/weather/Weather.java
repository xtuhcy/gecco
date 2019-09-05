package com.geccocrawler.gecco.demo.weather;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.spider.JsonBean;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Gecco(matchUrl = "http://t.weather.sojson.com/api/weather/city/{code}", pipelines = "consolePipeline")
public class Weather implements JsonBean {

    public static final String CITY_BASE_INFO_FILEPATH = "D:\\meituan\\opensource\\gecco\\src\\test\\resources\\city.json";
    public static final String WEATHER_URI = "http://t.weather.sojson.com/api/weather/city/";
    /**
     * date : 20190903
     * data : {"shidu":"66%","yesterday":{"date":"02","ymd":"2019-09-02","high":"高温 33℃","sunrise":"05:39","fx":"西南风","week":"星期一","low":"低温 23℃","fl":"<3级","sunset":"18:42","aqi":87,"type":"晴","notice":"愿你拥有比阳光明媚的心情"},"pm25":23,"pm10":44,"ganmao":"各类人群可自由活动","forecast":[{"date":"03","ymd":"2019-09-03","high":"高温 33℃","sunrise":"05:40","fx":"东南风","week":"星期二","low":"低温 22℃","fl":"<3级","sunset":"18:41","aqi":84,"type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","ymd":"2019-09-04","high":"高温 32℃","sunrise":"05:41","fx":"东南风","week":"星期三","low":"低温 22℃","fl":"<3级","sunset":"18:39","aqi":124,"type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"05","ymd":"2019-09-05","high":"高温 33℃","sunrise":"05:41","fx":"东南风","week":"星期四","low":"低温 22℃","fl":"<3级","sunset":"18:38","aqi":116,"type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"06","ymd":"2019-09-06","high":"高温 32℃","sunrise":"05:42","fx":"东北风","week":"星期五","low":"低温 23℃","fl":"<3级","sunset":"18:36","aqi":110,"type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","ymd":"2019-09-07","high":"高温 33℃","sunrise":"05:43","fx":"东南风","week":"星期六","low":"低温 24℃","fl":"<3级","sunset":"18:35","aqi":144,"type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"08","ymd":"2019-09-08","high":"高温 34℃","sunrise":"05:44","fx":"西南风","week":"星期日","low":"低温 24℃","fl":"<3级","sunset":"18:33","aqi":181,"type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"09","ymd":"2019-09-09","high":"高温 34℃","sunrise":"05:45","fx":"西南风","week":"星期一","low":"低温 24℃","fl":"<3级","sunset":"18:32","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"10","ymd":"2019-09-10","high":"高温 27℃","sunrise":"05:46","fx":"东南风","week":"星期二","low":"低温 20℃","fl":"<3级","sunset":"18:30","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"11","ymd":"2019-09-11","high":"高温 28℃","sunrise":"05:47","fx":"南风","week":"星期三","low":"低温 20℃","fl":"<3级","sunset":"18:28","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"12","ymd":"2019-09-12","high":"高温 28℃","sunrise":"05:48","fx":"东南风","week":"星期四","low":"低温 21℃","fl":"<3级","sunset":"18:27","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"13","ymd":"2019-09-13","high":"高温 30℃","sunrise":"05:49","fx":"西南风","week":"星期五","low":"低温 23℃","fl":"<3级","sunset":"18:25","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"14","ymd":"2019-09-14","high":"高温 30℃","sunrise":"05:50","fx":"东北风","week":"星期六","low":"低温 22℃","fl":"<3级","sunset":"18:23","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"15","ymd":"2019-09-15","high":"高温 22℃","sunrise":"05:50","fx":"东北风","week":"星期日","low":"低温 13℃","fl":"4-5级","sunset":"18:22","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"16","ymd":"2019-09-16","high":"高温 16℃","sunrise":"05:51","fx":"东北风","week":"星期一","low":"低温 13℃","fl":"<3级","sunset":"18:20","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"17","ymd":"2019-09-17","high":"高温 17℃","sunrise":"05:52","fx":"东北风","week":"星期二","low":"低温 13℃","fl":"3-4级","sunset":"18:19","type":"小雨","notice":"雨虽小，注意保暖别感冒"}],"wendu":"22","quality":"优"}
     * cityInfo : {"parent":"天津","city":"天津市","citykey":"101030100","updateTime":"05:40"}
     * time : 2019-09-04 06:18:48
     * message : success感谢又拍云(upyun.com)提供CDN赞助
     * status : 200
     */
    @JSONPath("$.date")
    private String date;

    @JSONPath("$.data")
    private DataEntity data;

    @JSONPath("$.cityInfo")
    private CityInfoEntity cityInfo;

    @JSONPath("$.time")
    private String time;

    @JSONPath("$.message")
    private String message;

    @JSONPath("$.status")
    private int status;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCityInfo(CityInfoEntity cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public CityInfoEntity getCityInfo() {
        return cityInfo;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }


    public static void main(String[] args) {
        List<HttpGetRequest> requests = new ArrayList<>();
        List<CityData> cityDatas = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(CITY_BASE_INFO_FILEPATH);
            String json = IOUtils.toString(is, CharacterEnum.UTF8.getName());
            cityDatas = JSONArray.parseObject(json, new TypeReference<List<CityData>>() {
            }.getType());
            for (CityData cityData : cityDatas) {
                if (!cityData.getCityCode().isEmpty())
                    requests.add(new HttpGetRequest(WEATHER_URI + cityData.getCityCode()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo.weather")
                //开始抓取的页面地址
                .start(requests)
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(2000)
                .start();
    }
}
