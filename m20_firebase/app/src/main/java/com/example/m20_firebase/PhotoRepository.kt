package com.example.m20_firebase


import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepository(applicationContext: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "db",
    ).build()


    suspend fun getAllPhotos(): List<Photo> {
        return withContext(Dispatchers.IO) { db.photoDao().getAll() }
    }

    suspend fun addPhoto(photo: Photo) {
        withContext(Dispatchers.IO) { db.photoDao().insert(photo) }
    }

}