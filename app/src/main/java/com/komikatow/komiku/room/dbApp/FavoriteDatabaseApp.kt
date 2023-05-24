package com.komikatow.komiku.room.dbApp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.komikatow.komiku.room.enity.EnityFavorite

@Database(entities = [EnityFavorite::class], version = 1)
abstract class FavoriteDatabaseApp: RoomDatabase() {


}