package me.tadebois.properties.model

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.tadebois.properties.api.Property
import me.tadebois.properties.api.PropertyApi
import me.tadebois.properties.ui.Helpers.getMockProperty
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PropertyRepositoryTest {

    @Mock
    private lateinit var propertyApi: PropertyApi

    private lateinit var propertyRepository: PropertyRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        propertyRepository = PropertyRepository(propertyApi)
    }

    @Test
    fun getProperties_success() = runTest {
        val expected = listOf(getMockProperty())
        `when`(propertyApi.getProperties()).thenReturn(flowOf(expected))

        val actualFlow = propertyRepository.getProperties()

        val actual = mutableListOf<Property>()
        actualFlow.collect { properties ->
            actual.addAll(properties)
        }

        assertEquals(expected, actual)
    }
}
