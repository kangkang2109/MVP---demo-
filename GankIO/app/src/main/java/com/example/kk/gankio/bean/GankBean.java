package com.example.kk.gankio.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kk on 2017/7/21.
 */

public class GankBean implements Parcelable{

    private String id;
    private String createdAt;
    private String desc;
    private String publishAt;
    private String source;
    private String type;
    private String url;
    private String images;
    private String who;

    public GankBean() {
    }

    protected GankBean(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        images = in.readString();
        who = in.readString();
    }

    public static final Creator<GankBean> CREATOR = new Creator<GankBean>() {
        @Override
        public GankBean createFromParcel(Parcel in) {
            return new GankBean(in);
        }

        @Override
        public GankBean[] newArray(int size) {
            return new GankBean[size];
        }
    };

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

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

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "GankBean{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", images='" + images + '\'' +
                ", who='" + who + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 必须按照顺序写
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.desc);
        parcel.writeString(this.publishAt);
        parcel.writeString(this.source);
        parcel.writeString(this.type);
        parcel.writeString(this.url);
        parcel.writeString(this.images);
        parcel.writeString(this.who);
    }


}
