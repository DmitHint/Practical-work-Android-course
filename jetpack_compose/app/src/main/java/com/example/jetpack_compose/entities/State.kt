package com.example.jetpack_compose.entities

sealed class State {
    object Start : State()
    object Loading : State()
    object Success : State()
}
