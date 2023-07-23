package me.tadebois.properties.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tadebois.properties.R
import me.tadebois.properties.api.Property
import me.tadebois.properties.ui.Helpers.getProperty
import me.tadebois.properties.ui.theme.PropertiesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiesScreen(propertyViewModel: PropertyViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            val propertiesState by propertyViewModel.properties.collectAsState(emptyList())
            PropertyList(properties = propertiesState)
        }
    }
}

@Composable
fun PropertyList(properties: List<Property>) {
    Column {
        properties.forEach { property ->
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
fun PropertyItem(
    property: Property,
    modifier: Modifier = Modifier
) {
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
