package com.example.jetpack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "counter",
        modifier = modifier
    ) {
        composable("counter") {
            CounterScreen(
                onNavigateToDetail = {
                    navController.navigate("detail/$it")
                }
            )
        }

        composable(
            route = "detail/{count}",
            arguments = listOf(
                navArgument("count") { type = NavType.IntType }
            )
        ) {
            val count = it.arguments?.getInt("count") ?: 0

            DetailScreen(
                count = count,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}