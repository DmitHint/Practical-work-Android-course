package com.example.dependency

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DaggerModule::class],
)
interface DaggerComponent {

    fun wheelDealer(): WheelDealer

    fun bicycleFactory(): BicycleFactory
}

@Module
class DaggerModule(private val frameFactory: FrameFactory) {

    @Provides
    @Singleton
    fun getWheelDealer(): WheelDealer = WheelDealer()

    @Provides
    @Singleton
    fun getBicycleFactory(): BicycleFactory = BicycleFactory(getWheelDealer(), frameFactory)

}
