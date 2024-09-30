package com.example.lol_champions_browser.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.networking.RemoteApi
import com.example.lol_champions_browser.ui.theme.FeraDemais
import com.example.lol_champions_browser.ui.theme.GoldLol
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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.preview),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (championList.isEmpty()) {
                item { Text(text = "Carregando campeões...") }
            } else {
                items(championList.size) { index ->
                    val champion = championList[index]
                    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

                    LaunchedEffect(champion.icon) {
                        imageBitmap = withContext(Dispatchers.IO) {
                            loadImageFromUrl(champion.icon)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(width = 2.dp, color = FeraDemais)
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
                        Box(modifier = Modifier.fillMaxSize()) {
                            imageBitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    text = champion.name,
                                    color = GoldLol
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = champion.title,
                                    color = GoldLol
                                )
                            }
                        }
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
