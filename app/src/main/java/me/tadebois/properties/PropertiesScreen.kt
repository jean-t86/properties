package me.tadebois.properties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.tadebois.properties.Helpers.getProperty
import me.tadebois.properties.propertyapi.ApiResponse
import me.tadebois.properties.propertyapi.Property
import me.tadebois.properties.propertyapi.PropertyApi
import me.tadebois.properties.propertyapi.PropertyApiService
import me.tadebois.properties.ui.theme.PropertiesTheme

@Composable
fun PropertiesScreen() {
    var propertyApiResponse by remember { mutableStateOf(ApiResponse(listOf())) }
    val scope = rememberCoroutineScope()

    Column {
        LaunchedEffect(Unit) {
            // Fetch properties when the composable is first launched
            scope.launch {
                // TODO: Refactor to use ViewModel
                propertyApiResponse = PropertyApi(
                    object : PropertyApiService {
                        override suspend fun getProperties(): ApiResponse {
                            return ApiResponse(listOf(getProperty()))
                        }
                    }
                ).getProperties()
            }
        }

        propertyApiResponse.data.forEach { property ->
            PropertyItem(property)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertiesScreenPreview() {
    PropertiesTheme {
        PropertiesScreen()
    }
}

@Composable
fun PropertyItem(property: Property, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Property ID: ${property.id}")
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "Description: ${property.description}")
    }
}

@Preview
@Composable
fun PropertyItemPreview() {
    PropertiesTheme {
        PropertyItem(
            getProperty()
        )
    }
}
