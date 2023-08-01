package me.tadebois.properties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.tadebois.properties.ui.PropertiesScreen
import me.tadebois.properties.ui.PropertyDetailsScreen
import me.tadebois.properties.ui.PropertyViewModel
import me.tadebois.properties.ui.SplashScreen
import me.tadebois.properties.ui.theme.PropertiesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val propertyViewModel: PropertyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PropertiesTheme {
                App(Modifier, propertyViewModel)
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Route.SPLASH_SCREEN.name) {
        composable(route = Route.SPLASH_SCREEN.name) {
            SplashScreen(modifier = modifier, propertyViewModel) {
                navController.navigate(Route.PROPERTIES_SCREEN.name) {
                    popUpTo(Route.SPLASH_SCREEN.name) { inclusive = true }
                }
            }
        }
        composable(route = Route.PROPERTIES_SCREEN.name) {
            PropertiesScreen(modifier = modifier, propertyViewModel) { property ->
                navController.navigate("${Route.PROPERTY_DETAILS_SCREEN.name}/${property.id}")
            }
        }
        composable(
            route = "${Route.PROPERTY_DETAILS_SCREEN.name}/{${NavArgument.PROPERTY_ID.name}}",
            arguments = listOf(
                navArgument(NavArgument.PROPERTY_ID.name) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString(NavArgument.PROPERTY_ID.name)
            PropertyDetailsScreen(propertyId, modifier = modifier)
        }
    }
}

enum class Route(name: String) {
    SPLASH_SCREEN("splashScreen"),
    PROPERTIES_SCREEN("propertiesScreen"),
    PROPERTY_DETAILS_SCREEN("propertyDetailsScreen")
}

enum class NavArgument(name: String) {
    PROPERTY_ID("propertyId")
}
