package com.komikatow.komiku.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.komikatow.komiku.room.enity.ModelChapter;

import java.util.List;

@Dao
public interface HistryDao {

    @Query("SELECT * FROM modelchapter")
    List<ModelChapter> getAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ModelChapter modelChapter);

    @Delete(entity = ModelChapter.class)
    void delete(ModelChapter  modelChapter);

    @Query("SELECT EXISTS (SELECT 1 FROM modelchapter WHERE endPointDetail = :endpoint)")
    boolean checkIfExist(String endpoint);

}
