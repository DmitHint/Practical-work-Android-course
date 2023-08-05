package com.example.jetpack_compose

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class CharacterInfoViewModel : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Start)
    val state = _state.asStateFlow()
    lateinit var character: Character


    //    suspend fun performRequest(characterId: Int): Pair<Character, MutableList<Episode>> {
    suspend fun performRequest(characterId: Int): Character {
        _state.value = State.Loading
        character = retrofit.getCharacterInfo(characterId)
        _state.value = State.Success
        return character
    }

    suspend fun getEpisodes(): MutableList<Episode> {
        val episodes = mutableListOf<Episode>()
        character.episode.forEach { url ->
            var id = ""
            var index = url.length - 1
            while (index >= 0 && url[index] != '/') {
                id += url[index]
                index--
            }
            episodes.add(retrofit.getEpisodeInfo(id.reversed().toInt()))
        }
        return episodes
    }
}