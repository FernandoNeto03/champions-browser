package com.example.lol_champions_browser.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ItemModel
import com.example.lol_champions_browser.networking.RemoteApi
import com.example.lol_champions_browser.ui.theme.FeraDemais
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.viewmodel.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

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

@Composable
fun DrawTeamActivity(navController: NavHostController, context: Context) {
    var championList by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var currentPage by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var team1 by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var team2 by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    val teamOne = stringResource(id = R.string.teamOne)
    val teamTwo = stringResource(id = R.string.teamTwo)
    val coroutineScope = rememberCoroutineScope()


    suspend fun shuffleTeams() {
        isLoading = true

        val newChampions = withContext(Dispatchers.IO) {
            RemoteApi(context).getAllChampions(currentPage)
        }

        if (newChampions.isNotEmpty()) {
            val shuffledChampions = newChampions.shuffled(Random)
            team1 = shuffledChampions.take(5)
            team2 = shuffledChampions.drop(5).take(5)
            championList = championList + newChampions
            currentPage++
        }
        isLoading = false
    }

    LaunchedEffect(Unit) {
        shuffleTeams()
    }

    Scaffold(
        topBar = {
            TopBarComponent(stringResource(id = R.string.teamDrawer))
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aram),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = teamOne,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp),
                            color = GoldLol
                        )
                        TeamDisplay(team = team1)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = teamTwo,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp),
                            color = GoldLol
                        )
                        TeamDisplay(team = team2)
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.buttonrefreshover),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                        .size(32.dp)
                        .clickable {
                            coroutineScope.launch {
                                shuffleTeams()
                            }
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            shareTeams(context, team1, team2, teamOne, teamTwo)
                        }
                    ) {
                        Text(stringResource(id = R.string.shareTeams))
                    }
                }
            }
        }
    )
}

fun getLaneIconResource(index: Int): Int {
    return when (index) {
        0 -> R.drawable.top
        1 -> R.drawable.jungle
        2 -> R.drawable.middle
        3 -> R.drawable.bottom
        4 -> R.drawable.utility
        else -> R.drawable.iconx
    }
}

@Composable
fun TeamDisplay(team: List<ChampionModel>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        team.forEachIndexed { index, champion ->
            ChampionCard(champion = champion, laneIcon = getLaneIconResource(index), itemViewModel = ItemViewModel())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ChampionCard(
    champion: ChampionModel,
    laneIcon: Int,
    itemViewModel: ItemViewModel
) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showItemsDialog by remember { mutableStateOf(false) }
    var randomItems by remember { mutableStateOf<List<ItemModel>>(emptyList()) }

    LaunchedEffect(champion.icon) {
        imageBitmap = withContext(Dispatchers.IO) {
            loadImageFromUrl(champion.icon)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                randomItems = itemViewModel.getRandomItemsForChampion()
                showItemsDialog = true
            }
    ) {
        Card(
            modifier = Modifier
                .size(75.dp)
                .border(width = 2.dp, color = FeraDemais)
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
            }
        }

        Image(
            painter = painterResource(id = laneIcon),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .padding(top = 4.dp)
        )
    }

    if (showItemsDialog) {
        AlertDialog(
            onDismissRequest = { showItemsDialog = false },
            title = { Text("Itens de ${champion.name}") },
            text = {
                Column {
                    randomItems.forEach { item ->
                        Text(item.name)
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showItemsDialog = false }) {
                    Text("Fechar")
                }
            }
        )
    }
}


fun shareTeams(context: Context, team1: List<ChampionModel>, team2: List<ChampionModel>, teamOne: String, teamTwo: String) {
    val team1Names = team1.joinToString(", ") { it.name }
    val team2Names = team2.joinToString(", ") { it.name }

    val shareText = """
        $teamOne: $team1Names
        $teamTwo: $team2Names
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Compartilhar equipes via"))
}
