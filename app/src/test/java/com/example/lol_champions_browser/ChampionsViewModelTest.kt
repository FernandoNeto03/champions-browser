package com.example.lol_champions_browser

import android.content.Context
import android.content.res.Resources
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ChampionStatsModel
import com.example.lol_champions_browser.viewmodel.ChampionsViewModel
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChampionsViewModelTest {

    private lateinit var viewModel: ChampionsViewModel
    private val mockContext: Context = mockk()
    private val mockResources: Resources = mockk()

    @Before
    fun setup() {
        viewModel = ChampionsViewModel()

        every { mockContext.resources } returns mockResources
    }

    @Test
    fun `selectChampion updates selectedChampion`() {
        val champion = createSampleChampionModel()

        viewModel.selectChampion(champion)

        assertEquals(champion, viewModel.selectedChampion)
    }

    private fun createSampleChampionModel(): ChampionModel {
        return ChampionModel(
            id = "sample_champion",
            key = "123",
            name = "Sample Champion",
            title = "The Example",
            tags = listOf("Mage"),
            icon = "icon_url",
            spriteUrl = "sprite_url",
            spriteX = 0,
            spriteY = 0,
            description = "This is a sample champion.",
            stats = ChampionStatsModel(
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
        )
    }
}
