package com.example.lol_champions_browser

import androidx.compose.runtime.mutableStateOf
import com.example.lol_champions_browser.data.ItemRepository
import com.example.lol_champions_browser.model.ItemGold
import com.example.lol_champions_browser.model.ItemImage
import com.example.lol_champions_browser.model.ItemModel
import com.example.lol_champions_browser.viewmodel.ItemViewModel
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ItemViewModelGetRandomTest {

    @Test
    fun `getRandomItemsForChampion returns 6 random items when sufficient items are available`() = runTest {
        // Mock do repositório (não será usado neste teste)
        val mockRepository = mockk<ItemRepository>()

        // Cria a ViewModel com o mock do repositório
        val viewModel = ItemViewModel(mockRepository)

        // Lista simulada de itens
        val mockItems = List(10) { index ->
            ItemModel(
                id = "$index",
                name = "Item$index",
                description = "Description$index",
                image = ItemImage("icon$index"),
                gold = ItemGold(100, 200, 50, true),
                tags = emptyList()
            )
        }

        // Atualiza diretamente o estado interno `_items`
        viewModel::class.java.getDeclaredField("_items").apply {
            isAccessible = true
            set(viewModel, mutableStateOf(mockItems))
        }

        // Obtém 6 itens aleatórios
        val randomItems = viewModel.getRandomItemsForChampion()

        // Verifica se o número de itens retornados é 6
        assertEquals(6, randomItems.size)
    }
}
