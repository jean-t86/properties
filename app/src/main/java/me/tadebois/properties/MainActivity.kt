package me.tadebois.properties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
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
                MainApp(propertyViewModel)
            }
        }
    }
}

@Composable
fun MainApp(propertyViewModel: PropertyViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splashScreen") {
        composable(route = "splashScreen") {
            SplashScreen(propertyViewModel) {
                navController.navigate("propertiesScreen") {
                    popUpTo("splashScreen") { inclusive = true }
                }
            }
        }
        composable(route = "propertiesScreen") {
            PropertiesScreen(propertyViewModel) { property ->
                navController.navigate("propertyDetailsScreen/${property.id}")
            }
        }
        composable(
            route = "propertyDetailsScreen/{propertyId}",
            arguments = listOf(
                navArgument("propertyId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId")
            PropertyDetailsScreen(propertyId)
        }
    }
}
