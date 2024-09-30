package com.example.lol_champions_browser.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.Image

@Composable
fun HomeActivity(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var championList by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        championList = withContext(Dispatchers.IO) {
            RemoteApi().getChampionsByTag("Assassin")
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (championList.isEmpty()) {
            Text(text = "Carregando campeões...")
        } else {
            championList.forEach { champion ->
                var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

                LaunchedEffect(champion.icon) {
                    imageBitmap = withContext(Dispatchers.IO) {
                        loadImageFromUrl(champion.icon)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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

fun loadImageFromUrl(url: String): Bitmap? {
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