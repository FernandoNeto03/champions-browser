package com.example.lol_champions_browser.activities

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.ui.theme.SuperBlue
import com.example.lol_champions_browser.ui.theme.teste

@Composable
fun HomeActivity(navController: NavHostController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("") }
    val tags = listOf("Tank", "Mage", "Assassin", "Marksman", "Support", "Fighter")

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
                Text(text = "Ver todos os campeões")
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
                Text(text = "Ver campeões por função")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Selecione uma função",
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
                                        text = tag,
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
                        Text(text = "Cancelar")
                    }
                },
                containerColor = teste,
                titleContentColor = GoldLol
            )
        }
    }
}
