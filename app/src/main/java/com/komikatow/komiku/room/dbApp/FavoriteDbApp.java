package com.komikatow.komiku.room.dbApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komikatow.komiku.room.dao.FavoriteDao;
import com.komikatow.komiku.room.enity.FavoriteEnity;

@Database(entities = {FavoriteEnity.class}, version = 1, exportSchema = false)
public abstract class FavoriteDbApp extends RoomDatabase {

    public abstract FavoriteDao dao();
    private static FavoriteDbApp INSTANCE;

    public static synchronized FavoriteDbApp getInstance(Context context) {

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDbApp.class, "Fav.db").build();

        }
        return INSTANCE;
    }


}
