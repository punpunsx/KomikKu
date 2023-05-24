package com.komikatow.komiku.room.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EnityFavorite(

        @PrimaryKey val endpoint:String,
        @ColumnInfo(name = "Title") val title:String,
        @ColumnInfo(name = "Thumb") val thumbnail:String

)