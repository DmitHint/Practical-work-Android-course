package com.example.dependency

class FrameFactory {
    private var serialNumber: Int = 0

    fun getFrame(color: Int): Frame = Frame(serialNumber++, color)
}
