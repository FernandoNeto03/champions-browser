package com.example.lol_champions_browser

import AllChampionsActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lol_champions_browser.activities.ChampionsByTagActivity
import com.example.lol_champions_browser.activities.HomeActivity

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable(route = "allChampions") { AllChampionsActivity(modifier, navController) }
        composable(route = "home") { HomeActivity(navController) }

        composable(
            route = "tagChampions/{tag}",
            arguments = listOf(navArgument("tag") { type = NavType.StringType })
        ) { backStackEntry ->
            val tag = backStackEntry.arguments?.getString("tag") ?: ""
            ChampionsByTagActivity(tag = tag, modifier = modifier, navController = navController)
        }
    })
}
