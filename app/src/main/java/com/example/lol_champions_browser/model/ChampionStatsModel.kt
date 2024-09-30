package com.example.lol_champions_browser.model

data class ChampionStatsModel(
    val hp: Int,
    val hpPerLevel: Int,
    val mp: Int,
    val mpPerLevel: Int,
    val moveSpeed: Int,
    val armor: Double,
    val armorPerLevel: Double,
    val spellBlock: Double,
    val spellBlockPerLevel: Double,
    val attackRange: Int,
    val hpRegen: Double,
    val hpRegenPerLevel: Double,
    val mpRegen: Int,
    val mpRegenPerLevel: Int,
    val crit: Int,
    val critPerLevel: Int,
    val attackDamage: Int,
    val attackDamagePerLevel: Int,
    val attackSpeedPerLevel: Double,
    val attackSpeed: Double
)