package com.example.dependency

import androidx.lifecycle.ViewModel

class BicycleViewModel(
    private val bicycleFactory: BicycleFactory,
): ViewModel() {
}