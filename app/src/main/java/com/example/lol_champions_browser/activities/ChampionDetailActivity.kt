package com.example.lol_champions_browser.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lol_champions_browser.ViewModel.ChampionViewModel
import com.example.lol_champions_browser.components.SystemBarColor
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.ui.theme.SuperBlue

@Composable
fun ChampionDetailActivity(modifier: Modifier = Modifier, viewModel: ChampionViewModel = viewModel()) {
    val champion = viewModel.selectedChampion

    SystemBarColor(SuperBlue)

    Scaffold(
        topBar = {
            TopBarComponent(text = "Detalhes do Campeão")
        }
    ) { innerPadding ->
        champion?.let { champ ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = "Nome: ${champ.name}", fontWeight = FontWeight.Bold)
                Text(text = "Título: ${champ.title}")
                Text(text = "Descrição: ${champ.description}")
                Text(text = "Tags: ${champ.tags.joinToString(", ")}")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Atributos:")
                Text(text = "HP: ${champ.stats.hp}")
                Text(text = "MP: ${champ.stats.mp}")
                Text(text = "Velocidade de Movimento: ${champ.stats.moveSpeed}")
                Text(text = "Dano de Ataque: ${champ.stats.attackDamage}")
            }
        } ?: run {
            Text(
                text = "Campeão não encontrado",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    }
}

