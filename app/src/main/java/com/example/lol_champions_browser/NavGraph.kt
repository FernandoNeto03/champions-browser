package com.example.lol_champions_browser

import AllChampionsActivity
import AllItemsActivity
import DrawTeamActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lol_champions_browser.ViewModel.ChampionViewModel
import com.example.lol_champions_browser.activities.ChampionDetailActivity
import com.example.lol_champions_browser.activities.ChampionsByTagActivity
import com.example.lol_champions_browser.activities.HomeActivity
import com.example.lol_champions_browser.activities.ItemDetailActivity

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val championViewModel: ChampionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable(route = "allChampions") {
            AllChampionsActivity(modifier, navController, championViewModel)
        }
        composable(route = "home") { HomeActivity(navController) }
        composable(route = "championDetail") {
            ChampionDetailActivity(modifier, championViewModel)
        }
        composable(
            route = "tagChampions/{tag}",
            arguments = listOf(navArgument("tag") { type = NavType.StringType })
        ) { backStackEntry ->
            val tag = backStackEntry.arguments?.getString("tag") ?: ""
            ChampionsByTagActivity(tag = tag, modifier = modifier, navController = navController)
        }

        composable("allItems") {
            AllItemsActivity(navController = navController)
        }

        composable(route = "itemDetail") {
            ItemDetailActivity()
        }

        composable("drawTeam") {
            DrawTeamActivity(navController = navController, context = LocalContext.current)
        }

    })
}



