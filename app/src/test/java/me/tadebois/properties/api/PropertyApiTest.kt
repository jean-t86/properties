package me.tadebois.properties.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import me.tadebois.properties.api.ApiResponseTestData.SAMPLE_PROPERTY_API_RESPONSE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
    fun getProperties_returnsListOfProperties() = runTest {
        val responseType = object : TypeToken<ApiResponse>() {}.type
        val apiResponse: ApiResponse = Gson().fromJson(
            SAMPLE_PROPERTY_API_RESPONSE,
            responseType
        )
        val expected = apiResponse.data
        Mockito.`when`(apiService.getProperties()).thenReturn(apiResponse)

        var actual: List<Property>? = null
        propertyApi.getProperties().collect {
            actual = it
        }

        assertNotNull(actual)
        Mockito.verify(apiService).getProperties()
        assertEquals(expected, actual)
    }

    @Test
    fun getProperties_canThrowAnException() = runTest {
        val mockedException = RuntimeException("API error")
        Mockito.`when`(apiService.getProperties()).thenThrow(mockedException)

        var exceptionThrown = false
        try {
            propertyApi.getProperties().collect {
                // Wait for an emit
            }
        } catch (e: Exception) {
            exceptionThrown = true
        }

        Mockito.verify(apiService).getProperties()
        assertTrue(exceptionThrown)
    }
}
