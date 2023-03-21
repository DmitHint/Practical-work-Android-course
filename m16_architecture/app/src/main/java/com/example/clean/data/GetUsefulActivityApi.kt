package com.example.clean.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://www.boredapi.com/"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val getUsefulActivityApi: GetUsefulActivityApi =
        retrofit.create(GetUsefulActivityApi::class.java)
}

interface GetUsefulActivityApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/api/activity/")
    suspend fun getActivity(): UsefulActivityDto
}
