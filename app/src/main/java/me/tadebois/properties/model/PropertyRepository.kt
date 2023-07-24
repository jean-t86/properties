package me.tadebois.properties.model

import kotlinx.coroutines.flow.Flow
import me.tadebois.properties.api.Property
import me.tadebois.properties.api.PropertyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyApi: PropertyApi) {
    suspend fun getProperties(): Flow<List<Property>> = propertyApi.getProperties()
}
