package com.example.dependency

import androidx.lifecycle.ViewModel

class BicycleViewModel(
    val bicycleFactory: BicycleFactory,
): ViewModel()