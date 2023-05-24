package com.komikatow.komiku.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.komikatow.komiku.room.enity.EnityFavorite

interface DaoFavorite {

    @Query("SELECT * FROM enityfavorite")
    fun getAllFav() : List<EnityFavorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(vararg enityFavorite: EnityFavorite)

    @Query("SELECT * FROM enityfavorite WHERE :endpoint = :endpoint")
    fun getItemWithId(endpoint:String)

    @Query("DELETE FROM enityfavorite WHERE :endpoint = :endpoint")
    fun deleteData(endpoint: String)

}