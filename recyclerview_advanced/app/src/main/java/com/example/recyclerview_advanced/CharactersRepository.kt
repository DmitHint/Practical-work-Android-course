package com.example.recyclerview_advanced

import android.util.Log
import kotlinx.coroutines.delay

class CharactersRepository {

    suspend fun getCharacters(page: Int): List<Character>{
        delay(2000)
        Log.d("API", retrofit.getCharacters(page).characters[0].name)
        return retrofit.getCharacters(page).characters
    }
}