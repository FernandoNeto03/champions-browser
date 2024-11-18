package com.example.lol_champions_browser

import com.example.lol_champions_browser.data.ItemRepository
import com.example.lol_champions_browser.model.ItemModel
import com.example.lol_champions_browser.viewmodel.ItemViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ItemViewModelTest {

    @Test
    fun `initial state is correct`() {

        val mockRepository = mockk<ItemRepository>()

        val viewModel = ItemViewModel(mockRepository)

        assertEquals(emptyList<ItemModel>(), viewModel.items.value)
        assertEquals(false, viewModel.isLoading.value)
    }
}

