package com.example.clean.data

import javax.inject.Inject


class UsefulActivitiesRepository @Inject constructor() {
    suspend fun getUsefulActivity(): UsefulActivityDto {
        return RetrofitInstance.getUsefulActivityApi.getActivity()
    }
}
