package com.example.lol_champions_browser.data

import com.example.lol_champions_browser.model.ItemModel

interface ItemRepository {
    suspend fun fetchItems(page: Int): List<ItemModel>
}
