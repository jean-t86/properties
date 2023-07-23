package me.tadebois.properties.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.tadebois.properties.api.Property
import me.tadebois.properties.model.PropertyRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _properties: MutableStateFlow<List<Property>> = MutableStateFlow(emptyList())
    val properties: StateFlow<List<Property>> get() = _properties

    suspend fun loadProperties() {
        try {
            propertyRepository.getProperties().collect { properties ->
                _properties.value = properties
            }
        } catch (e: Exception) {
            // TODO: Handle endpoint errors gracefully
            Timber.e(e.message)
        }
    }
}
