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

        val mockRepository = mockk<ItemRepository>()

        val viewModel = ItemViewModel(mockRepository)

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

        viewModel::class.java.getDeclaredField("_items").apply {
            isAccessible = true
            set(viewModel, mutableStateOf(mockItems))
        }

        val randomItems = viewModel.getRandomItemsForChampion()

        assertEquals(6, randomItems.size)
    }
}
