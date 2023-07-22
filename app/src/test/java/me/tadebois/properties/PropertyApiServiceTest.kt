package me.tadebois.properties

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import me.tadebois.properties.ApiResponseTestData.SAMPLE_PROPERTY_API_RESPONSE
import me.tadebois.properties.propertyapi.PropertyApi
import me.tadebois.properties.propertyapi.PropertyApiService
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
    fun getProperties_returnsListOfProperties() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(SAMPLE_PROPERTY_API_RESPONSE))

        val properties = propertyApi.getProperties().data

        assertNotNull(properties)
        assertEquals(3, properties.count())
        assertEquals(Helpers.getProperty(), properties[0])

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/properties", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
    }
}
