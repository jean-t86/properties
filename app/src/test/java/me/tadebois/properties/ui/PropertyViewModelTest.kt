package me.tadebois.properties.ui

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import me.tadebois.properties.MainDispatcherRule
import me.tadebois.properties.api.Property
import me.tadebois.properties.model.PropertyRepository
import me.tadebois.properties.ui.Helpers.getProperty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PropertyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var propertyRepository: PropertyRepository

    @Mock
    private lateinit var application: Application

    private lateinit var propertyViewModel: PropertyViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        propertyViewModel = PropertyViewModel(application, propertyRepository)
    }

    @Test
    fun loadProperties_success() = runTest {
        val expected = listOf(getProperty())
        `when`(propertyRepository.getProperties()).thenReturn(flowOf(expected))

        propertyViewModel.loadProperties()
        advanceUntilIdle()

        val actual = propertyViewModel.properties.value
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun loadProperties_error() = runTest {
        val mockException = RuntimeException("API error")
        `when`(propertyRepository.getProperties()).thenThrow(mockException)
        val expected = emptyList<Property>()

        propertyViewModel.loadProperties()
        advanceUntilIdle()

        val actual = propertyViewModel.properties.value
        assertEquals(expected, actual)
    }
}
