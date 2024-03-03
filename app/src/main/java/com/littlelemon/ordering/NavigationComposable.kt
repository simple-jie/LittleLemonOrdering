package com.littlelemon.ordering

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    context: Context,
    databaseItems: State<List<MenuItemRoom>>
) {
    val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    NavHost(
        navController = navController,
        startDestination = if (sharedPreferences.all.isEmpty()) {
            Onboarding.route
        } else {
            Home.route
        }
    ) {
        composable(Onboarding.route) {
            OnboardingScreen(navController, context)
        }
        composable(Home.route) {
            HomeScreen(navController, databaseItems)
        }
        composable(Profile.route) {
            ProfileScreen(context, navController)
        }
    }
}