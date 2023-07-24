package me.tadebois.properties.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import me.tadebois.properties.R
import me.tadebois.properties.api.Property
import me.tadebois.properties.ui.Helpers.formatAgentName
import me.tadebois.properties.ui.Helpers.formatPropertyType
import me.tadebois.properties.ui.Helpers.getProperty
import me.tadebois.properties.ui.Helpers.getStreetAddress
import me.tadebois.properties.ui.Helpers.getSuburb
import me.tadebois.properties.ui.theme.PropertiesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiesScreen(
    propertyViewModel: PropertyViewModel = viewModel(),
    onPropertyTapped: (property: Property) -> Unit
) {
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
            PropertyList(properties = propertiesState, onPropertyTapped)
        }
    }
}

@Composable
fun PropertyList(
    properties: List<Property>,
    onPropertyTapped: (property: Property) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(properties) { _, property ->
            PropertyItem(property, { onPropertyTapped(property) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertiesScreenPreview() {
    PropertiesTheme {
        PropertiesScreen {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyItem(
    property: Property,
    onPropertyTapped: (property: Property) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        onClick = { onPropertyTapped(property) }
    ) {
        Column {
            PropertyImagePager(propertyImages = property.propertyImages)
            Spacer(modifier = modifier.height(32.dp))
            Row(
                modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Column(modifier = modifier.weight(2f)) {
                    PropertyAddress(property)
                    Spacer(modifier = modifier.height(16.dp))
                    PropertyAmenities(property, modifier)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = modifier.weight(1f)
                ) {
                    PropertyAgent(property, modifier)
                }
            }
        }
    }
}

@Composable
private fun PropertyAmenities(
    property: Property,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(bottom = 16.dp)) {
        Text(text = "${property.bedrooms}", modifier = modifier.padding(end = 4.dp))
        Image(
            painter = painterResource(id = R.drawable.outline_bed_24),
            contentDescription = stringResource(R.string.bedrooms),
            alpha = 0.3f
        )
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = "${property.bathrooms}",
            modifier = modifier.padding(end = 4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.outline_bathtub_24),
            contentDescription = stringResource(R.string.bathrooms),
            alpha = 0.3f
        )
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = "${property.carspaces}",
            modifier = modifier.padding(end = 4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.outline_car_port_24),
            contentDescription = stringResource(R.string.car_spaces),
            alpha = 0.3f
        )
    }
}

@Composable
private fun PropertyAddress(
    property: Property,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatPropertyType(property),
        style = MaterialTheme.typography.titleLarge
    )
    Text(text = getStreetAddress(property) ?: "", modifier = modifier.alpha(0.3f))
    Text(text = getSuburb(property) ?: "", modifier = modifier.alpha(0.3f))
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PropertyAgent(
    property: Property,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(data = property.agent.avatar.small.url)
    Box {
        Surface(
            modifier = modifier
                .size(80.dp)
                .placeholder(
                    visible = true,
                    color = Color.Gray,
                    // optional, defaults to RectangleShape
                    shape = CircleShape,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    ),
                )
        ) { }
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.agent_photo),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
        )
    }
    Spacer(modifier = modifier.width(8.dp))
    Text(
        text = formatAgentName(property),
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(showBackground = true)
@Composable
fun PropertyItemPreview() {
    PropertiesTheme {
        PropertyItem(
            getProperty(),
            {}
        )
    }
}
