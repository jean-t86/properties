package me.tadebois.properties.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PropertyApi(private val apiService: PropertyApiService) {
    suspend fun getProperties(): Flow<List<Property>> = flow {
        emit(apiService.getProperties().data)
    }
}
