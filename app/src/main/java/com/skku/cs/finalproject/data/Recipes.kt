package com.skku.cs.finalproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "source_id")
    var sourceId: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)