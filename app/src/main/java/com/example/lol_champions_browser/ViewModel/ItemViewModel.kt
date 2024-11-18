package com.example.lol_champions_browser.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_champions_browser.data.ItemRepository
import com.example.lol_champions_browser.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private var _items = mutableStateOf<List<ItemModel>>(emptyList())
    val items: State<List<ItemModel>> = _items

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var currentPage = 1
    private var hasMorePages = true

    fun fetchItems() {
        if (_isLoading.value || !hasMorePages) return
        _isLoading.value = true
        viewModelScope.launch {
            val itemList = withContext(Dispatchers.IO) {
                repository.fetchItems(currentPage)
            }
            if (itemList.isNotEmpty()) {
                _items.value += itemList
                currentPage++
            } else {
                hasMorePages = false
            }
            _isLoading.value = false
        }
    }

    fun getRandomItemsForChampion(): List<ItemModel> {
        return if (_items.value.size >= 6) {
            _items.value.shuffled().take(6)
        } else {
            emptyList()
        }
    }

    fun getItemById(itemId: String): ItemModel? {
        return items.value.find { it.id == itemId }
    }
}
