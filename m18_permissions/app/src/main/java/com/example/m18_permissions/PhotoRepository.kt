package com.example.m18_permissions

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

suspend fun getPhotos(page: Int): List<Photo> {
    delay(2000)
//        return withContext(Dispatchers.IO) {
//            photoDao.getAll()
    return mutableListOf()
}