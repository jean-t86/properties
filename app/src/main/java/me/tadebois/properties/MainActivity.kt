package me.tadebois.properties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.tadebois.properties.api.Property
import me.tadebois.properties.ui.PropertiesScreen
import me.tadebois.properties.ui.PropertyViewModel
import me.tadebois.properties.ui.SplashScreen
import me.tadebois.properties.ui.theme.PropertiesTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val propertyViewModel: PropertyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PropertiesTheme {
                SplashScreen(propertyViewModel) {
                    setContent {
                        PropertiesTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                PropertiesScreen(propertyViewModel, ::onPropertyTapped)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun onPropertyTapped(property: Property) {
        Timber.d("")
    }
}
