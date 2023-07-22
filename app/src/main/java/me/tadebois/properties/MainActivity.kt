package me.tadebois.properties

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tadebois.properties.propertyapi.ApiResponse
import me.tadebois.properties.propertyapi.PropertyApi
import me.tadebois.properties.ui.theme.PropertiesTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var propertyApi: PropertyApi // TODO: Refactor to use ViewModel

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PropertiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PropertiesScreen()
                }
            }
        }

        coroutineScope.launch {
            try {
                val response = propertyApi.getProperties()
                handleApiResponse(response)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        Timber.w("Network Layer Under Construction.")
    }

    private fun handleApiResponse(response: ApiResponse) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        Timber.w("Network Layer Under Construction")
    }
}
