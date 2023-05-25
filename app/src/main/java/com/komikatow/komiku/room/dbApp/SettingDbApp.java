package com.komikatow.komiku.room.dbApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komikatow.komiku.room.dao.SettingDao;
import com.komikatow.komiku.room.enity.SettingEnity;

@Database(entities = {SettingEnity.class}, version = 1)
public abstract class SettingDbApp extends RoomDatabase {

    private static SettingDbApp INSTACE;
    public abstract SettingDao dao();

    public static synchronized SettingDbApp  getInstance(Context context){

        if (INSTACE == null){
            INSTACE = Room.databaseBuilder(context.getApplicationContext(),
                    SettingDbApp.class, "Setting.db").build();
        }
        return INSTACE;
    }

}
