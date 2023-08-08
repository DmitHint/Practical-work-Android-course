package com.example.jetpack_compose.viewmodels

import androidx.lifecycle.ViewModel
import com.example.jetpack_compose.CharactersRepository
import com.example.jetpack_compose.entities.Location
import com.example.jetpack_compose.entities.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationListViewModel : ViewModel() {

    private val repository = CharactersRepository()

    suspend fun load(page: Int): List<Location> {
        return repository.getLocations(page)
    }

}