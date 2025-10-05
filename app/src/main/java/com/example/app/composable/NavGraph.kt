package com.example.app.composable

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.Routes
import com.example.app.interfaces.*

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginApp(
                onGoRegister = { navController.navigate(Routes.REGISTER) { launchSingleTop = true } },
                onGoForgot = { navController.navigate(Routes.FORGOT) { launchSingleTop = true } },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }; launchSingleTop = true
                    }
                },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) { launchSingleTop = true } }
            )
        }

        composable(Routes.REGISTER) {
            RegisterApp(
                onBack = { navController.popBackStack() },
                onRegistered = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.FORGOT) {
            ForgotPasswordScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.HOME) {
            HomeApp(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSettings = {
                    navController.navigate(Routes.SETTINGS) { launchSingleTop = true }
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
