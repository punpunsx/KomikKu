package com.komikatow.komiku.room.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SettingEnity  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @ColumnInfo(name = "image Quality")
    private boolean quality;

    @ColumnInfo(name = "cache foto")
    private boolean cache;

    @ColumnInfo(name = "animasi Detail")
    private boolean animasiDetail;

    @ColumnInfo(name = "animasi Transisi")
    private boolean transisi;

    @ColumnInfo(name = "bahasa aplikasi")
    private boolean bahasa;

    @ColumnInfo(name = "app Version")
    private boolean version;

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    public boolean getQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }

    public boolean getCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean getAnimasiDetail() {
        return animasiDetail;
    }

    public void setAnimasiDetail(boolean animasiDetail) {
        this.animasiDetail = animasiDetail;
    }

    public boolean getTransisi() {
        return transisi;
    }

    public void setTransisi(boolean transisi) {
        this.transisi = transisi;
    }

    public boolean getBahasa() {
        return bahasa;
    }

    public void setBahasa(boolean bahasa) {
        this.bahasa = bahasa;
    }

    public boolean getVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }
}
