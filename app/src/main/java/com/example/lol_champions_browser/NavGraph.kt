package com.example.lol_champions_browser


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lol_champions_browser.activities.HomeActivity

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost( navController = navController, startDestination = "home", builder = {
        composable(route = "home") {  HomeActivity(modifier, navController) }
    })

}