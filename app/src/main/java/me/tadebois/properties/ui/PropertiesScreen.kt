package me.tadebois.properties.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
        PropertyImagePager(propertyImages = property.propertyImages)
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PropertyImagePager(
    propertyImages: List<PropertyImage>,
    modifier: Modifier = Modifier
) {
    val imageUrls = propertyImages.map { it.attachment.thumb.url }
    val context = LocalContext.current
    val imageBitmaps = loadAndCachePropertyImages(imageUrls, context)

    Box(
        modifier = modifier.aspectRatio(16 / 9f)
    ) {
        val state = rememberPagerState(
            initialOffscreenLimit = 2,
            initialPage = 0,
            pageCount = imageBitmaps.size
        )

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = state
        ) { page ->
            val imageBitmap by imageBitmaps[page]
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

        // Custom Pager Indicator
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val currentPage = state.currentPage.toFloat()
            imageBitmaps.forEachIndexed { index, _ ->
                val color = if (currentPage == index.toFloat()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
                Spacer(modifier = Modifier.size(8.dp))
                IndicatorCircle(color = color)
            }
        }
    }
}

@Composable
private fun loadAndCachePropertyImages(
    imageUrls: List<String>,
    context: Context
): List<MutableState<ImageBitmap?>> {
    // Cache the images using remember with the image URL as custom key
    val imageBitmaps = remember(imageUrls) {
        imageUrls.map { imageUrl ->
            mutableStateOf<ImageBitmap?>(null).apply {
                // Load the image asynchronously using Coil's painter
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .target { drawable ->
                        value = (drawable as BitmapDrawable).bitmap.asImageBitmap()
                    }
                    .build()

                ImageLoader(context).enqueue(request)
            }
        }
    }
    return imageBitmaps
}

@Composable
fun IndicatorCircle(
    color: Color,
    diameter: Dp = 10.dp
) {
    Surface(
        modifier = Modifier.size(diameter),
        shape = CircleShape,
        color = color
    ) {}
}

