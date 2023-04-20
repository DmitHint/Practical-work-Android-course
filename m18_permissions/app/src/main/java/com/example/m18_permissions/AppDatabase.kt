package com.example.m18_permissions

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Photo::class,
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao

}