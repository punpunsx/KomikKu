package com.komikatow.komiku.model;

public final class ModelChapter <T> {

    private T nemeCh;
    private T endPointCh;

    //variabel berikut digunakan untuk database riwayat
    private T endPointDetail;
    private T date;
    private T nameKomik;
    private T thumbnail;

    public T getNemeCh() {
        return nemeCh;
    }

    public void setNemeCh(T nemeCh) {
        this.nemeCh = nemeCh;
    }

    public T getEndPointCh() {
        return endPointCh;
    }

    public void setEndPointCh(T endPointCh) {
        this.endPointCh = endPointCh;
    }

    public T getEndPointDetail() {
        return endPointDetail;
    }

    public void setEndPointDetail(T endPointDetail) {
        this.endPointDetail = endPointDetail;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public T getNameKomik() {
        return nameKomik;
    }

    public void setNameKomik(T nameKomik) {
        this.nameKomik = nameKomik;
    }

    public T getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(T thumbnail) {
        this.thumbnail = thumbnail;
    }
}
