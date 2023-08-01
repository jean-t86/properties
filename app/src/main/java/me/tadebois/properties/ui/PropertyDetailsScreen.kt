package me.tadebois.properties.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.tadebois.properties.R

@Composable
fun PropertyDetailsScreen(
    propertyId: String?,
    modifier: Modifier = Modifier
) {
    ScreenHeader(
        title = stringResource(id = R.string.property_details_screen),
        modifier = modifier
    ) {
        Text(
            text = "PropertyId: $propertyId",
            modifier = modifier.padding(16.dp)
        )
    }
}
