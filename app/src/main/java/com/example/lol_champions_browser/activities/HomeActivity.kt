package com.example.lol_champions_browser.activities

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@Composable
fun HomeActivity(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("") }
    val tags = listOf("Tank", "Mage", "Assassin", "Marksman", "Support", "Fighter")

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
                .padding(8.dp)
        ) {
            Text(text = "Ver todos os campeões")
        }

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Ver campeões por função")
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Selecione uma função") },
            text = {
                Column {
                    tags.forEach { tag ->
                        TextButton(
                            onClick = {
                                selectedTag = tag
                                showDialog = false
                                navController.navigate("tagChampions/${tag}")
                            }
                        ) {
                            Text(text = tag)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
