package me.tadebois.properties.ui

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tadebois.properties.R
import me.tadebois.properties.api.Property
import me.tadebois.properties.model.PropertyRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val application: Application,
    private val propertyRepository: PropertyRepository
) : AndroidViewModel(application) {

    val properties: StateFlow<List<Property>> get() = _properties
    private val _properties: MutableStateFlow<List<Property>> = MutableStateFlow(emptyList())

    val propertiesLoaded: State<Boolean> get() = _propertiesLoaded
    private val _propertiesLoaded = mutableStateOf(false)

    val error: State<String?> get() = _error
    private val _error = mutableStateOf<String?>(null)

    suspend fun loadProperties() {
        try {
            propertyRepository.getProperties().collect { properties ->
                _properties.value = properties
                _propertiesLoaded.value = true
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            /*
            This delay is in service of better UI/UX. It shows the user that the application
            tried to fetch the data but failed. A delay will allow the user to see the
            loading spinner on the splash screen each time Retry is tapped.
             */
            delay(500)
            _error.value = application.getString(R.string.connection_error)
        }
    }

    fun retryLoadProperties() {
        _error.value = null
        viewModelScope.launch {
            loadProperties()
        }
    }
}
