package com.komikatow.komiku.room.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class AdvanceSizeEnity {

    @PrimaryKey
    @NonNull
    private int uid;

    @ColumnInfo(name = "slider size")
    private String slider;

    @ColumnInfo(name = "detail size")
    private String detail;

    public AdvanceSizeEnity(){}

    public AdvanceSizeEnity(String slider, String detail) {
        this.slider = slider;
        this.detail = detail;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
