package com.komikatow.komiku.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.komikatow.komiku.room.enity.SettingEnity;

import java.util.List;

@Dao
public interface SettingDao {

    @Query("SELECT * FROM settingenity")
    List<SettingEnity > getAll();

    @Insert(entity = SettingEnity.class)
    void insert(SettingEnity enity);

    @Insert(entity = SettingEnity.class)
    void delete(SettingEnity  enity);

    @Query("SELECT * FROM settingenity WHERE uid = :id")
    SettingEnity getById(int id);

}
