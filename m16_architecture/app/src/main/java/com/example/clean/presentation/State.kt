package com.example.clean.presentation

sealed class State {
    object Start : State()
    object Loading : State()
    object Success : State()
}
