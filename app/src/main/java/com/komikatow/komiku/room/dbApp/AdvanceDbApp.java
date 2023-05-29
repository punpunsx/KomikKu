package com.komikatow.komiku.room.dbApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komikatow.komiku.room.dao.AdvanceSizeDao;
import com.komikatow.komiku.room.enity.AdvanceSizeEnity;

@Database(entities = {AdvanceSizeEnity.class} ,version = 1, exportSchema = false)
public abstract class AdvanceDbApp extends RoomDatabase {

    public abstract AdvanceSizeDao dao();
    private static AdvanceDbApp INSTANCE;

    public synchronized static AdvanceDbApp getInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AdvanceDbApp.class, "advanceSetting.db").build();
        }
        return INSTANCE;
    }
}
