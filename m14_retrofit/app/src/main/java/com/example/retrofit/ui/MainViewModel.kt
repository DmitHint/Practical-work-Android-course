package com.example.retrofit.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.retrofit.ResultsUserModel
import com.example.retrofit.RetrofitInstance
import com.example.retrofit.User
import com.example.retrofit.databinding.FragmentMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Start)
    val state = _state.asStateFlow()

    suspend fun searchUser(): User {
        _state.value = State.Loading
        val response = RetrofitInstance.searchUserApi.getUser()
        val user = response.results[0]
        _state.value = State.Success
        return user
    }

}