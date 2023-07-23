package me.tadebois.properties.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import me.tadebois.properties.R
import me.tadebois.properties.api.Property
import me.tadebois.properties.api.PropertyImage
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
fun PropertyList(
    properties: List<Property>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
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
        ImageCarousel(propertyImages = property.propertyImages)
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyItemPreview() {
    PropertiesTheme {
        PropertyItem(
            getProperty()
        )
    }
}

@Composable
fun ImageCarousel(
    propertyImages: List<PropertyImage>,
    modifier: Modifier = Modifier
) {
    val imageUrls = propertyImages.map { it.attachment.medium.url }
    val context = LocalContext.current

    // Load and cache all the images only once, after the composable is committed
    val imageBitmaps by rememberUpdatedState(newValue = runBlocking {
        imageUrls.map { imageUrl ->
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA) // Cache the original image size

            withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .apply(requestOptions)
                    .submit() // Load the image synchronously (non-blocking)
                    .get() // Get the result (blocking)
                    .asImageBitmap() // Convert to ImageBitmap
            }
        }
    })

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    ) {
        itemsIndexed(imageBitmaps) { _, imageBitmap ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            ) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
