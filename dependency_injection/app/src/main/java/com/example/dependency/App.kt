package com.example.dependency

import android.app.Application

class App: Application() {
    companion object{
        lateinit var daggerComponent: DaggerComponent
        lateinit var frameFactory: FrameFactory
    }

    override fun onCreate() {
        super.onCreate()

        frameFactory = FrameFactory()
//        daggerComponent = DaggerComponent.builder
    }
}