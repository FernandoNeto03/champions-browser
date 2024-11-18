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
        // Mock do repositório
        val mockRepository = mockk<ItemRepository>()

        // Cria a ViewModel com o mock do repositório
        val viewModel = ItemViewModel(mockRepository)

        // Verifica o estado inicial da ViewModel
        assertEquals(emptyList<ItemModel>(), viewModel.items.value)
        assertEquals(false, viewModel.isLoading.value)
    }
}

