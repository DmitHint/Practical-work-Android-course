package com.example.jetpack_compose.api

import com.example.jetpack_compose.entities.Character
import com.example.jetpack_compose.entities.CharacterList
import com.example.jetpack_compose.entities.Episode
import com.example.jetpack_compose.entities.LocationList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterList

    @GET("/api/location")
    suspend fun getLocations(
        @Query("page") page: Int
    ): LocationList

    @GET("/api/character/{id}")
    suspend fun getCharacterInfo(@Path("id") id: Int): Character

    @GET("/api/episode/{id}")
    suspend fun getEpisodeInfo(@Path("id") id: Int): Episode
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://rickandmortyapi.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(CharactersApi::class.java)