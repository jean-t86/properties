package me.tadebois.properties.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import me.tadebois.properties.api.ApiResponseTestData.SAMPLE_PROPERTY_API_RESPONSE
import me.tadebois.properties.ui.Helpers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertyApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var propertyApi: PropertyApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson: Gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val apiService = retrofit.create(PropertyApiService::class.java)
        propertyApi = PropertyApi(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getProperties_returnsListOfProperties() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(SAMPLE_PROPERTY_API_RESPONSE))

        var properties: List<Property>? = null
        propertyApi.getProperties().collect {
            properties = it
        }

        assertNotNull(properties)
        assertEquals(3, properties?.count())
        assertEquals(Helpers.getProperty(), properties?.getOrNull(0))

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/properties", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
    }
}
