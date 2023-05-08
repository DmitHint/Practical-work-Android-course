package com.example.m19_location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun getAll(): List<Photo>

    @Insert(entity = Photo::class)
    fun insert(photo: Photo)
}
