package com.example.retrofit.ui

sealed class State {
    object Start: State()
    object Loading: State()
    object Success: State()
}