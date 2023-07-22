package me.tadebois.properties

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import me.tadebois.properties.ApiResponseTestData.SAMPLE_PROPERTY_API_RESPONSE
import me.tadebois.properties.propertyapi.ApiResponse
import me.tadebois.properties.propertyapi.PropertyApi
import me.tadebois.properties.propertyapi.PropertyApiService
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PropertyApiTest {

    @Mock
    private lateinit var apiService: PropertyApiService

    private lateinit var propertyApi: PropertyApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        propertyApi = PropertyApi(apiService)
    }

    @Test
    fun getProperties_returnsListOfProperties() = runBlocking {
        val responseType = object : TypeToken<ApiResponse>() {}.type
        val expected: ApiResponse = Gson().fromJson(
            SAMPLE_PROPERTY_API_RESPONSE,
            responseType
        )
        Mockito.`when`(apiService.getProperties()).thenReturn(expected)

        val actual = propertyApi.getProperties()

        Mockito.verify(apiService).getProperties()
        assertEquals(expected, actual)
    }

    @Test
    fun getProperties_canThrowAnException() = runBlocking {
        val mockedException = RuntimeException("API error")
        Mockito.`when`(apiService.getProperties()).thenThrow(mockedException)

        var exceptionThrown = false
        try {
            propertyApi.getProperties()
        } catch (e: Exception) {
            exceptionThrown = true
        }

        Mockito.verify(apiService).getProperties()
        Assert.assertTrue(exceptionThrown)
    }
}
