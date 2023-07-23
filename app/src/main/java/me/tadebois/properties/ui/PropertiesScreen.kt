package me.tadebois.properties.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
    val imageUrls = propertyImages.map { it.attachment.thumb.url }
    val context = LocalContext.current

    // Cache the images using remember with a custom key
    val imageBitmaps = remember(imageUrls) {
        imageUrls.map { imageUrl ->
            mutableStateOf<ImageBitmap?>(null).apply {
                // Load the image asynchronously using Glide's target
                Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .transition(BitmapTransitionOptions.withCrossFade()) // Crossfade transition
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            value = resource.asImageBitmap()
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Not used
                        }
                    })
            }
        }
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    ) {
        itemsIndexed(imageBitmaps) { index, imageBitmapState ->
            val imageBitmap by imageBitmapState

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            ) {
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}




