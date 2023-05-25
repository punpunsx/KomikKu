package com.komikatow.komiku.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.komikatow.komiku.room.enity.FavoriteEnity;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favoriteenity")
    List<FavoriteEnity> getAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(FavoriteEnity favoriteEnity);

    @Delete(entity = FavoriteEnity.class)
    void deleteData(FavoriteEnity enity);

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteenity WHERE Endpoint = :endpoint)")
    boolean checkIfExist(String endpoint);
}
