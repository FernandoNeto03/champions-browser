package com.example.lol_champions_browser


import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ChampionStatsModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ChampionModelTest {

    @Test
    fun `ChampionModel holds correct values`() {
        val stats = ChampionStatsModel(
            hp = 500,
            hpPerLevel = 80,
            mp = 300,
            mpPerLevel = 40,
            moveSpeed = 330,
            armor = 20.0,
            armorPerLevel = 3.5,
            spellBlock = 30.0,
            spellBlockPerLevel = 0.5,
            attackRange = 550,
            hpRegen = 6.0,
            hpRegenPerLevel = 0.8,
            mpRegen = 8,
            mpRegenPerLevel = 0,
            crit = 0,
            critPerLevel = 0,
            attackDamage = 55,
            attackDamagePerLevel = 3,
            attackSpeedPerLevel = 2.0,
            attackSpeed = 0.625
        )

        val champion = ChampionModel(
            id = "ahri",
            key = "1",
            name = "Ahri",
            title = "The Nine-Tailed Fox",
            tags = listOf("Mage", "Assassin"),
            icon = "icon_url",
            spriteUrl = "sprite_url",
            spriteX = 48,
            spriteY = 48,
            description = "Ahri is a mage and assassin.",
            stats = stats
        )

        assertEquals("ahri", champion.id)
        assertEquals("Ahri", champion.name)
        assertEquals("The Nine-Tailed Fox", champion.title)
        assertEquals("icon_url", champion.icon)
        assertEquals("sprite_url", champion.spriteUrl)
        assertEquals(stats, champion.stats)
    }
}
