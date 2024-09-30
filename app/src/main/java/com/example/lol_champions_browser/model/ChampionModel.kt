package com.example.lol_champions_browser.model

data class ChampionModel(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val tags: List<String>,
    val icon: String,
    val spriteUrl: String,
    val spriteX: Int,
    val spriteY: Int,
    val description: String,
    val stats: ChampionStatsModel
)