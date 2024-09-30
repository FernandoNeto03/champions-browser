package com.example.lol_champions_browser.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.networking.RemoteApi


@Composable
fun HomeActivity(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
    ) {
       RemoteApi().getFact()
    }
}