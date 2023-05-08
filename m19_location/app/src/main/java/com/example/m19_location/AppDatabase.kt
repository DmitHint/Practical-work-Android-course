package com.example.m19_location

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