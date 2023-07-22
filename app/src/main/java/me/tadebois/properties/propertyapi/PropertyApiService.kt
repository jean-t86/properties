package me.tadebois.properties.propertyapi

import retrofit2.Call
import retrofit2.http.GET

interface PropertyApiService {
    @GET("properties")
    fun getProperties(): Call<List<Property>>
}
