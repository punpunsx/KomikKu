package com.komikatow.komiku.room.enity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class FavoriteEnity {

    @PrimaryKey
    @NonNull
    private String Endpoint;

    @ColumnInfo(name = "name")
    private String title;

    @ColumnInfo(name = "thumb")
    private String thumbnail;

    public FavoriteEnity(){

    }

    public FavoriteEnity(@NonNull String endpoint, String title, String thumbnail) {
        Endpoint = endpoint;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    @NonNull
    public String getEndpoint() {
        return Endpoint;
    }

    public void setEndpoint(@NonNull String endpoint) {
        Endpoint = endpoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
