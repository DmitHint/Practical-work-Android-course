package com.example.recyclerview_advanced

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {
    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterList
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