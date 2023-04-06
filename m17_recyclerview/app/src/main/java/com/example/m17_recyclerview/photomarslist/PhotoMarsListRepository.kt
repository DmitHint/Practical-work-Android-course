package com.example.m17_recyclerview.photomarslist

import com.example.m17_recyclerview.api.retrofit
import com.example.m17_recyclerview.models.PhotoMars
import kotlinx.coroutines.delay

class PhotoMarsListRepository {
    suspend fun getMarsPhotos(page: Int): List<PhotoMars>{
        delay(2000)
        return retrofit.photos(page).photos
    }
}