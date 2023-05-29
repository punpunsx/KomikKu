package com.komikatow.komiku.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.komikatow.komiku.room.enity.AdvanceSizeEnity;

import java.util.List;

@Dao
public interface AdvanceSizeDao {

    @Query("SELECT * FROM advancesizeenity")
    List<AdvanceSizeEnity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AdvanceSizeEnity enity);

    @Delete(entity = AdvanceSizeEnity.class)
    void delete(AdvanceSizeEnity enity);

    @Query("SELECT EXISTS (SELECT 1 FROM advancesizeenity WHERE `slider size` = :slider)")
    boolean checkIfSliderExist(String slider);

    @Query("SELECT EXISTS (SELECT 1 FROM advancesizeenity WHERE `detail size` = :detail)")
    boolean checkIfDetailExist(String detail);

}
