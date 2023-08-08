package com.example.jetpack_compose.viewmodels

import androidx.lifecycle.ViewModel
import com.example.jetpack_compose.entities.Character
import com.example.jetpack_compose.CharactersRepository
import com.example.jetpack_compose.entities.Episode
import com.example.jetpack_compose.entities.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterInfoViewModel : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Start)
    val state = _state.asStateFlow()

    lateinit var character: Character
    lateinit var episodes: MutableList<Episode>
    private val repository = CharactersRepository()


    suspend fun performRequest(characterId: Int) {
        _state.value = State.Loading
        val (characterResult, episodesResult) = repository.getCharacterInfo(characterId)
        character = characterResult
        episodes = episodesResult
        _state.value = State.Success
    }
}