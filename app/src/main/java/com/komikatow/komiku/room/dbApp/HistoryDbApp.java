package com.komikatow.komiku.room.dbApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komikatow.komiku.room.enity.ModelChapter;
import com.komikatow.komiku.room.dao.HistryDao;

@Database(entities = {ModelChapter.class}, version = 1, exportSchema = false)
public abstract class HistoryDbApp extends RoomDatabase {

    public abstract HistryDao dao();
    private static HistoryDbApp INSTANCE;

    public static synchronized HistoryDbApp getInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    HistoryDbApp.class, "riwayat.db").build();

        }
        return INSTANCE;
    }

}
