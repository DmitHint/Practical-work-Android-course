package com.example.clean.di

import com.example.clean.presentation.MainViewModelFactory
import dagger.Component

@Component
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory
}
