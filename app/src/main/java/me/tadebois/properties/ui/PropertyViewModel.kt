package me.tadebois.properties.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    val properties: StateFlow<List<Property>> get() = _properties
    private val _properties: MutableStateFlow<List<Property>> = MutableStateFlow(emptyList())

    val propertiesLoaded: State<Boolean> get() = _propertiesLoaded
    private val _propertiesLoaded = mutableStateOf(false)

    suspend fun loadProperties() {
        try {
            propertyRepository.getProperties().collect { properties ->
                _properties.value = properties
                _propertiesLoaded.value = true
            }
        } catch (e: Exception) {
            // TODO: Handle endpoint errors gracefully
            Timber.e(e.message)
        }
    }
}
