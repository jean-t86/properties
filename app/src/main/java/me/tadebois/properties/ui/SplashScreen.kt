package me.tadebois.properties.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tadebois.properties.R

@Composable
fun SplashScreen(
    propertyViewModel: PropertyViewModel = viewModel(),
    onSplashScreenFinished: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        LaunchedEffect(key1 = true, block = {
            propertyViewModel.loadProperties()
            onSplashScreenFinished()
        })

        Image(
            painter = painterResource(R.drawable.sentia_logo),
            contentDescription = "Properties Splash Screen",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(64.dp)
        )
    }
}
