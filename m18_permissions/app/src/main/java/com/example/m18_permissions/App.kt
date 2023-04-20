package com.example.m18_permissions

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var db: AppDatabase
        private set

}