package me.tadebois.properties.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tadebois.properties.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel(),
    onSplashScreenFinished: () -> Unit
) {
    val propertiesLoaded by propertyViewModel.propertiesLoaded
    val error by propertyViewModel.error

    LaunchedEffect(propertiesLoaded) {
        if (!propertiesLoaded && error == null) {
            propertyViewModel.loadProperties()
        }

        if (error == null) {
            onSplashScreenFinished()
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        val (logo, circularProgressIndicator, errorColumn) = createRefs()

        Image(
            painter = painterResource(R.drawable.sentia_logo),
            contentDescription = "Properties Splash Screen",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .padding(64.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        if (error != null) {
            // Display error message and retry button
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .constrainAs(errorColumn) {
                        top.linkTo(logo.bottom)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error!!,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                val hapticFeedback = LocalHapticFeedback.current
                Button(
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        propertyViewModel.retryLoadProperties()
                    },
                    modifier = Modifier.width(140.dp)
                ) {
                    Text(text = stringResource(R.string.retry))
                }
            }

        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(circularProgressIndicator) {
                        top.linkTo(logo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                strokeWidth = 4.dp
            )
        }
    }
}
