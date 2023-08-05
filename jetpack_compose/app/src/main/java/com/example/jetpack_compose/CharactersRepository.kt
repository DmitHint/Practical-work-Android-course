package com.example.jetpack_compose

import kotlinx.coroutines.delay

class CharactersRepository {

    suspend fun getCharacters(page: Int): List<Character>{
        delay(2000)
        return retrofit.getCharacters(page).results
    }

    suspend fun getCharacterInfo(id: Int): Character{
        delay(2000)
        return retrofit.getCharacterInfo(id)
    }
}