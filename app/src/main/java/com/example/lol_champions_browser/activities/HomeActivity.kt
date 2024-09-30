package com.example.lol_champions_browser.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.networking.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeActivity(modifier: Modifier, navController: NavHostController) {

    var championList by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        championList = withContext(Dispatchers.IO) {
            RemoteApi().getChampionsByTag("Assassin") //1a letra maiuscula
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (championList.isEmpty()) {
            Text(text = "Carregando campeões...")
        } else {
            championList.forEach { champion ->
                Text(text = champion)
            }
        }
    }
}
