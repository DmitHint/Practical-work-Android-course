package com.example.mvvm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Start)
    val state = _state.asStateFlow()

    fun onSearchClick(text: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            delay(5_000)
            _state.value = State.Success(text)
        }
    }
}