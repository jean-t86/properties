package me.tadebois.properties.api

import retrofit2.http.GET

interface PropertyApiService {
    @GET("properties")
    suspend fun getProperties(): ApiResponse
}
