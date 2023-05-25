package com.komikatow.komiku.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.komikatow.komiku.room.enity.SettingEnity;

import java.util.List;

@Dao
public interface SettingDao <T>{

    @Query("SELECT * FROM settingenity")
    List<SettingEnity <T> > getAll();

    @Insert(entity = SettingEnity.class)
    void insert(SettingEnity <T> enity);

    @Insert(entity = SettingEnity.class)
    void delete(SettingEnity <T> enity);

    @Query("SELECT * FROM settingenity WHERE uid = :id")
    SettingEnity<T> getById(int id);

}
