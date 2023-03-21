package com.example.clean.domain

import com.example.clean.data.UsefulActivitiesRepository
import com.example.clean.data.UsefulActivityDto
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val usefulActivitiesRepository: UsefulActivitiesRepository,
) {
    suspend fun execute(): UsefulActivityDto {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}