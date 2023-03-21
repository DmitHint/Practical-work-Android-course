package com.example.clean.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clean.data.UsefulActivityDto
import com.example.clean.domain.GetUsefulActivityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Start)
    val state = _state.asStateFlow()

    lateinit var activity: UsefulActivityDto

    fun reloadUsefulActivity() {
        viewModelScope.launch {
            _state.value = State.Loading
            activity = getUsefulActivityUseCase.execute()
            _state.value = State.Success
        }
    }
}
