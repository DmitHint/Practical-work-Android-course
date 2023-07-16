package com.example.recyclerview_advanced

import android.util.Log
import kotlinx.coroutines.delay

class CharactersRepository {

    suspend fun getCharacters(page: Int): List<Character>{
        delay(2000)
        return retrofit.getCharacters(page).results
    }
}