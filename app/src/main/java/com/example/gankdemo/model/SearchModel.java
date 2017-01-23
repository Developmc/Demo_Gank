package com.example.gankdemo.model;

import com.google.gson.annotations.SerializedName;

/**搜索结果的model
 * Created by clement on 17/1/23.
 */

public class SearchModel {

    /**
     * desc : 还在用ListView？
     * ganhuo_id : 57334c9d67765903fb61c418
     * publishedAt : 2016-05-12T12:04:43.857000
     * readability : <div><div class="show-content"><blockquote>
     <blockquote><p>&#x9020;&#x8D77;&#x6765;&#xFF01;&#x5C0F;&#x4F19;&#x4F34;&#x4EEC;&#xFF01;</p></blockquote>

     </div>
     </div>
     * type : Android
     * url : http://www.jianshu.com/p/a92955be0a3e
     * who : 陈宇明
     */

    private String desc;
    @SerializedName("ganhuo_id")
    private String id;
    private String publishedAt;
    private String readability;
    private String type;
    private String url;
    private String who;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getReadability() {
        return readability;
    }

    public void setReadability(String readability) {
        this.readability = readability;
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

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
