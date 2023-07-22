package me.tadebois.properties.propertyapi

import retrofit2.http.GET

interface PropertyApiService {
    @GET("properties")
    suspend fun getProperties(): ApiResponse
}
