package me.tadebois.properties.propertyapi

class PropertyApi(private val apiService: PropertyApiService) {
    suspend fun getProperties(): ApiResponse {
        return apiService.getProperties()
    }
}
