package io.opentelemetry.kotlin.telescope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.opentelemetry.kotlin.telescope.telemetry.SessionTelemetry

@Composable
fun TelescopeShopApp(sessionTelemetry: SessionTelemetry) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "shop") {
        composable("shop") {
            LaunchedEffect(Unit) { sessionTelemetry.onNavigation("TelescopeShopScreen") }
            _root_ide_package_.io.opentelemetry.kotlin.telescope.TelescopeShopScreen(navController)
        }
        composable("confirmation") {
            LaunchedEffect(Unit) { sessionTelemetry.onNavigation("ConfirmationScreen") }
            _root_ide_package_.io.opentelemetry.kotlin.telescope.ConfirmationScreen()
        }
    }
}
