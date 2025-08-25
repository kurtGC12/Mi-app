package com.example.app.composable

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.Routes
import com.example.app.interfaces.LoginScreen
import com.example.app.interfaces.RegisterScreen
import com.example.app.interfaces.ForgotPasswordScreen


@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN){

        composable(Routes.LOGIN){
            LoginScreen(
                onGoRegister ={
                    navController.navigate(Routes.REGISTER) {
                        launchSingleTop = true }
                },
                onGoForgot = {
                    navController.navigate(Routes.FORGOT) {
                        launchSingleTop = true
                    }
                })
        }
        composable(Routes.REGISTER){
            RegisterScreen(
                onBack = { navController.popBackStack() })
        }
        composable(Routes.FORGOT){
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() })
        }
      }
}
