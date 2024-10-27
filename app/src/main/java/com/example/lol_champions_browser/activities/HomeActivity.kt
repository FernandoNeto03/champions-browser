package com.example.lol_champions_browser.activities

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.ui.theme.SuperBlue
import com.example.lol_champions_browser.ui.theme.teste
import java.util.Locale

@Composable
fun HomeActivity(navController: NavHostController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("") }
    val tags = listOf("Tank", "Mage", "Assassin", "Marksman", "Support", "Fighter")

    val tagTranslations = mapOf(
        "Tank" to stringResource(id = R.string.tank),
        "Mage" to stringResource(id = R.string.mage),
        "Assassin" to stringResource(id = R.string.assassin),
        "Marksman" to stringResource(id = R.string.marksman),
        "Support" to stringResource(id = R.string.support),
        "Fighter" to stringResource(id = R.string.fighter)
    )
    val isPortuguese = Locale.getDefault().language == "pt"

    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val hasPlayedAudio = sharedPreferences.getBoolean("has_played_audio", false)

        if (!hasPlayedAudio) {
            val mediaPlayer = MediaPlayer.create(context, R.raw.inicio)
            mediaPlayer.start()

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()

                sharedPreferences.edit().putBoolean("has_played_audio", true).apply()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.preview),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("allChampions")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuperBlue,
                    contentColor = GoldLol
                )
            ) {
                Text(text = stringResource(id = R.string.viewAllChampions))
            }

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuperBlue,
                    contentColor = GoldLol
                )
            ) {
                Text(text = stringResource(id = R.string.viewChampionsByRole))
            }

            Button(
                onClick = {
                    navController.navigate("drawTeam")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuperBlue,
                    contentColor = GoldLol
                )
            ) {
                Text(text = stringResource(id = R.string.teamDrawer))
            }
            Button(
                onClick = {
                    navController.navigate("allItems")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuperBlue,
                    contentColor = GoldLol
                )
            ) {
                Text(text = stringResource(id = R.string.viewAllItems))
            }
        }

        val displayedTags = tags.map { tagTranslations[it] ?: it }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.selectOneRole),
                        color = GoldLol
                    )
                },
                text = {
                    Column {
                        tags.forEach { tag ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = SuperBlue
                                )
                            ) {
                                TextButton(
                                    onClick = {
                                        selectedTag = tag
                                        showDialog = false
                                        navController.navigate("tagChampions/$tag")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (isPortuguese) {
                                            tagTranslations[tag] ?: tag
                                        } else {
                                            tag
                                        },
                                        color = GoldLol
                                    )
                                }

                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                },
                containerColor = teste,
                titleContentColor = GoldLol
            )
        }
    }
}
