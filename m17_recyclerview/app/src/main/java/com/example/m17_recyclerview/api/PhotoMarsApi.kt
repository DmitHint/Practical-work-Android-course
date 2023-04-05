package com.example.m17_recyclerview.api

import com.example.m17_recyclerview.models.PhotoMarsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoMarsApi {
    @Headers("X-API-KEY: $api_key")
    @GET("/mars-photos/api/v1/rovers/curiosity/photos/?earth_date=2022-1-1")
    suspend fun photos(
        @Query("page") page: Int
    ): PhotoMarsList

    private companion object {
        private const val api_key = "1P6BJcT0JktqorsygzOh24ILg8sATWCzUUxORjZA"
    }
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://api.nasa.gov")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(PhotoMarsApi::class.java)