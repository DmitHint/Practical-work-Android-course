package com.example.dependency

class WheelDealer {
    private var serialNumber = 0

    fun getWheel() : Wheel = Wheel(serialNumber++)
}
