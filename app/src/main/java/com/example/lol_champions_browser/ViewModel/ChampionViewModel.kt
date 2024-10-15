package com.example.lol_champions_browser.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lol_champions_browser.model.ChampionModel

class ChampionViewModel : ViewModel() {
    var selectedChampion by mutableStateOf<ChampionModel?>(null)

    fun selectChampion(champion: ChampionModel) {
        selectedChampion = champion
    }
}
