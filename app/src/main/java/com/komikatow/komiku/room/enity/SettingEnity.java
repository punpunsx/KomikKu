package com.komikatow.komiku.room.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SettingEnity <T> {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private T uid;

    @ColumnInfo(name = "image Quality")
    private int quality;

    @ColumnInfo(name = "cache foto")
    private T cache;

    @ColumnInfo(name = "animasi Detail")
    private T animasiDetail;

    @ColumnInfo(name = "animasi Transisi")
    private T transisi;

    @ColumnInfo(name = "bahasa aplikasi")
    private T bahasa;

    @ColumnInfo(name = "app Version")
    private T version;

    @NonNull
    public T getUid() {
        return uid;
    }

    public void setUid(@NonNull T uid) {
        this.uid = uid;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public T getCache() {
        return cache;
    }

    public void setCache(T cache) {
        this.cache = cache;
    }

    public T getAnimasiDetail() {
        return animasiDetail;
    }

    public void setAnimasiDetail(T animasiDetail) {
        this.animasiDetail = animasiDetail;
    }

    public T getTransisi() {
        return transisi;
    }

    public void setTransisi(T transisi) {
        this.transisi = transisi;
    }

    public T getBahasa() {
        return bahasa;
    }

    public void setBahasa(T bahasa) {
        this.bahasa = bahasa;
    }

    public T getVersion() {
        return version;
    }

    public void setVersion(T version) {
        this.version = version;
    }
}
