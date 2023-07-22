package me.tadebois.properties.propertyapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertyApi {
    private val apiService: PropertyApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://f213b61d-6411-4018-a178-53863ed9f8ec.mock.pstmn.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PropertyApiService::class.java)
    }

    suspend fun getProperties(): ApiResponse {
        return apiService.getProperties()
    }
}
