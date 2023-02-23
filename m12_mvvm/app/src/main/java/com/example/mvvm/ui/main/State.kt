package com.example.mvvm.ui.main

sealed class State {
    object Start : State()
    object Loading : State()
    data class Success(val text: String) : State()
}

