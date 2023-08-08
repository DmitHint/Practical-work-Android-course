package com.example.jetpack_compose

import com.example.jetpack_compose.api.retrofit
import com.example.jetpack_compose.entities.Character
import com.example.jetpack_compose.entities.Episode
import com.example.jetpack_compose.entities.Location
import kotlinx.coroutines.delay

class CharactersRepository {

    suspend fun getCharacters(page: Int): List<Character>{
        delay(2000)
        return retrofit.getCharacters(page).results
    }

    suspend fun getLocations(page: Int): List<Location>{
        delay(2000)
        return retrofit.getLocations(page).results
    }

    suspend fun getCharacterInfo(characterId: Int): Pair<Character, MutableList<Episode>>{
        delay(2000)
        val character = retrofit.getCharacterInfo(characterId)

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
        return Pair(character,episodes)
    }
}