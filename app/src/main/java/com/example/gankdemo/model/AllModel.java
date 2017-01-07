package com.example.gankdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**AllModel
 * Created by clement on 17/1/7.
 */

public class AllModel implements Serializable {

    /**
     * _id : 586dad46421aa93161103da1
     * createdAt : 2017-01-05T10:19:50.280Z
     * desc : 2016年软件漏洞排行榜Top50：Android以523处位居第一
     * publishedAt : 2017-01-05T13:18:10.185Z
     * source : chrome
     * type : 瞎推荐
     * url : https://mp.weixin.qq.com/s?__biz=MzIyMDEzMTA2MQ==&mid=2651148610&idx=1&sn=4c4026d79155e37879489e1d18d50e05&chksm=8c214ad8bb56c3ce52fa2bccfe007afc7fbd25ddadf9a7cc8e440ef37aef55e4a0cbf8181eb0&scene=0&key=ccc56fc127f128b7a9d7acf5a0e19e6f5b53f88173317fdeafd990e81277b282931c52689c0740f5c66a4a7fd157d2949a0e51efdc77b859588251c206b7d90003cf12ec7496a4a535d2e33a7f19a3d5&ascene=0&uin=MTY5MDI4NDA4Mg%3D%3D&devicetype=iMac+MacBookPro11%2C3+OSX+OSX+10.12.2+build(16C67)&version=12010210&nettype=WIFI&fontScale=100&pass_ticket=Z%2BTUepynoV6nLHmtJkY17ldjO2MxFchx6cuElgXvJZ8DDEmRDHiGUAFq6%2F7VVolE
     * used : true
     * who : LHF
     * images : ["http://img.gank.io/ac5b2472-0402-4d30-a329-08d09104b784"]
     */
    @SerializedName("_id")
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
