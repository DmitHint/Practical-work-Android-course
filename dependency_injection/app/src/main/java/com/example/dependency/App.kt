package com.example.dependency

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    companion object{
        lateinit var daggerComponent: DaggerComponent
        lateinit var frameFactory: FrameFactory
    }

    override fun onCreate() {
        super.onCreate()

        frameFactory = FrameFactory()
        daggerComponent = DaggerDaggerComponent.builder()
            .daggerModule(DaggerModule(frameFactory))
            .build()

        startKoin {
            modules(module{
                single { WheelDealer() }
                factory <BicycleFactory> { BicycleFactory(get(), frameFactory) }
            })
        }
    }
}