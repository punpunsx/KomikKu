package com.komikatow.komiku.room.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class ModelChapter  {

    //variabel berikut digunakan untuk database riwayat
    @PrimaryKey
    @NonNull
    private String endPointDetail;

    @ColumnInfo(name = "tanggal")
    private String date;

    @ColumnInfo(name = "namaKomik")
    private String nameKomik;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "chapter")
    private String nemeCh;

    public ModelChapter(@NonNull String endPointDetail, String date , String nameKomik,String nameCh ,String thumbnail){
        this.endPointDetail = endPointDetail;
        this.date = date;
        this.nameKomik = nameKomik;
        this.nemeCh = nameCh;
        this.thumbnail = thumbnail;
    }

    public ModelChapter(){}

    private String endPointCh;

    public String getNemeCh() {
        return nemeCh;
    }

    public void setNemeCh(String nemeCh) {
        this.nemeCh = nemeCh;
    }

    public String getEndPointCh() {
        return endPointCh;
    }

    public void setEndPointCh(String endPointCh) {
        this.endPointCh = endPointCh;
    }

    public String getEndPointDetail() {
        return endPointDetail;
    }

    public void setEndPointDetail(String endPointDetail) {
        this.endPointDetail = endPointDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameKomik() {
        return nameKomik;
    }

    public void setNameKomik(String nameKomik) {
        this.nameKomik = nameKomik;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
