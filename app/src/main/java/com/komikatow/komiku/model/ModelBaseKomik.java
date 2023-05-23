package com.komikatow.komiku.model;

public final class ModelBaseKomik <T> {

    private T endPoint;
    private T Title;
    private T thumbnail;
    private T type;

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public T getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(T endPoint) {
        this.endPoint = endPoint;
    }

    public T getTitle() {
        return Title;
    }

    public void setTitle(T title) {
        Title = title;
    }

    public T getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(T thumbnail) {
        this.thumbnail = thumbnail;
    }
}
