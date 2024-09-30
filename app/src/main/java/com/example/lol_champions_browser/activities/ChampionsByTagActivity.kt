package com.example.lol_champions_browser.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.networking.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ChampionsByTagActivity(tag: String, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var championList by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    LaunchedEffect(tag) {
        championList = withContext(Dispatchers.IO) {
            RemoteApi().getChampionsByTag(tag)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (championList.isEmpty()) {
            item { Text(text = "Carregando campeões...") }
        } else {
            items(championList) { champion ->
                var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

                LaunchedEffect(champion.icon) {
                    imageBitmap = withContext(Dispatchers.IO) {
                        loadImageFromUrl(champion.icon)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Campeão fera demaisssss: ${champion.name}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        imageBitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(end = 8.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(text = champion.name)
                    }
                }
            }
        }
    }
}

private fun loadImageFromUrl(url: String): Bitmap? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream = connection.inputStream
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}
