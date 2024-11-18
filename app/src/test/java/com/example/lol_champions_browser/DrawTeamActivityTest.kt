package com.example.lol_champions_browser

import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ChampionStatsModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ShareTeamsTest {
    @Test
    fun testShareTeamsText() {
        val team1 = listOf(
            ChampionModel(
                id = "1",
                key = "Ashe",
                name = "Ashe",
                title = "Frost Archer",
                tags = listOf("Marksman"),
                icon = "",
                spriteUrl = "",
                spriteX = 0,
                spriteY = 0,
                description = "A skilled archer from Freljord.",
                stats = ChampionStatsModel(
                    hp = 539,
                    hpPerLevel = 85,
                    mp = 280,
                    mpPerLevel = 32,
                    moveSpeed = 325,
                    armor = 34.0,
                    armorPerLevel = 34.0,
                    spellBlock = 30.0,
                    spellBlockPerLevel = 34.6,
                    attackRange = 600,
                    hpRegen = 0.5,
                    hpRegenPerLevel = 0.32,
                    mpRegen = 120,
                    mpRegenPerLevel = 1,
                    crit = 0,
                    critPerLevel = 0,
                    attackDamage = 52,
                    attackDamagePerLevel = 5,
                    attackSpeedPerLevel = 0.032,
                    attackSpeed = 0.654
                )
            ),
            ChampionModel(
                id = "2",
                key = "Garen",
                name = "Garen",
                title = "Might of Demacia",
                tags = listOf("Fighter"),
                icon = "",
                spriteUrl = "",
                spriteX = 0,
                spriteY = 0,
                description = "A mighty warrior from Demacia.",
                stats = ChampionStatsModel(
                    hp = 620,
                    hpPerLevel = 84,
                    mp = 0,
                    mpPerLevel = 0,
                    moveSpeed = 340,
                    armor = 36.0,
                    armorPerLevel = 34.0,
                    spellBlock = 32.0,
                    spellBlockPerLevel = 34.6,
                    attackRange = 175,
                    hpRegen = 8.0,
                    hpRegenPerLevel = 0.75,
                    mpRegen = 0,
                    mpRegenPerLevel = 0,
                    crit = 0,
                    critPerLevel = 0,
                    attackDamage = 66,
                    attackDamagePerLevel = 4,
                    attackSpeedPerLevel = 0.04,
                    attackSpeed = 0.6
                )
            )
        )
        val team2 = listOf(
            ChampionModel(
                id = "3",
                key = "Lux",
                name = "Lux",
                title = "Lady of Luminosity",
                tags = listOf("Mage"),
                icon = "",
                spriteUrl = "",
                spriteX = 0,
                spriteY = 0,
                description = "A mage from Demacia.",
                stats = ChampionStatsModel(
                    hp = 490,
                    hpPerLevel = 85,
                    mp = 480,
                    mpPerLevel = 25,
                    moveSpeed = 330,
                    armor = 19.0,
                    armorPerLevel = 34.0,
                    spellBlock = 30.0,
                    spellBlockPerLevel = 34.6,
                    attackRange = 550,
                    hpRegen = 5.5,
                    hpRegenPerLevel = 0.6,
                    mpRegen = 8,
                    mpRegenPerLevel = 0,
                    crit = 0,
                    critPerLevel = 0,
                    attackDamage = 53,
                    attackDamagePerLevel = 3,
                    attackSpeedPerLevel = 0.012,
                    attackSpeed = 0.625
                )
            ),
            ChampionModel(
                id = "4",
                key = "Ahri",
                name = "Ahri",
                title = "Nine-Tailed Fox",
                tags = listOf("Mage", "Assassin"),
                icon = "",
                spriteUrl = "",
                spriteX = 0,
                spriteY = 0,
                description = "A cunning fox from Ionia.",
                stats = ChampionStatsModel(
                    hp = 490,
                    hpPerLevel = 85,
                    mp = 480,
                    mpPerLevel = 25,
                    moveSpeed = 330,
                    armor = 19.0,
                    armorPerLevel = 34.0,
                    spellBlock = 30.0,
                    spellBlockPerLevel = 34.6,
                    attackRange = 550,
                    hpRegen = 5.5,
                    hpRegenPerLevel = 0.6,
                    mpRegen = 8,
                    mpRegenPerLevel = 0,
                    crit = 0,
                    critPerLevel = 0,
                    attackDamage = 53,
                    attackDamagePerLevel = 3,
                    attackSpeedPerLevel = 0.012,
                    attackSpeed = 0.625
                )
            )
        )
        val expectedText = """
            Equipe 1: Ashe, Garen
            Equipe 2: Lux, Ahri
        """.trimIndent()
        val actualText = generateShareText(team1, team2)
        assertEquals(expectedText, actualText)
    }
    private fun generateShareText(team1: List<ChampionModel>, team2: List<ChampionModel>): String {
        val team1Names = team1.joinToString(", ") { it.name }
        val team2Names = team2.joinToString(", ") { it.name }
        return """
            Equipe 1: $team1Names
            Equipe 2: $team2Names
        """.trimIndent()
    }
}