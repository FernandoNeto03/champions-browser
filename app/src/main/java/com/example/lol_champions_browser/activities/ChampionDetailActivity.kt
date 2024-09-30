package com.example.lol_champions_browser.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lol_champions_browser.ViewModel.ChampionViewModel

@Composable
fun ChampionDetailActivity(modifier: Modifier = Modifier, viewModel: ChampionViewModel = viewModel()) {
    val champion = viewModel.selectedChampion

    champion?.let { champ ->
        Column(modifier = modifier) {
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
        Text(text = "Campeão não encontrado")
    }
}

