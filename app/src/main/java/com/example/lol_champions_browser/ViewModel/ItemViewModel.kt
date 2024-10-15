package com.example.lol_champions_browser.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_champions_browser.model.ItemModel
import com.example.lol_champions_browser.model.ItemGold
import com.example.lol_champions_browser.model.ItemImage
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ItemViewModel : ViewModel() {
    private var _items = mutableStateOf<List<ItemModel>>(emptyList())
    val items: State<List<ItemModel>> = _items

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    var selectedItem by mutableStateOf<ItemModel?>(null)
        private set


    fun fetchItems() {
        _isLoading.value = true
        viewModelScope.launch {
            val itemList = withContext(Dispatchers.IO) {
                fetchItemsFromApi()
            }
            _items.value = itemList
            _isLoading.value = false
        }
    }

    fun selectItem(item: ItemModel) {
        Log.d("ItemViewModel", "Item selecionado: ${item.name}, ID: ${item.id}")
        selectedItem = item
    }

    private suspend fun fetchItemsFromApi(): List<ItemModel> {
        val apiUrl = "https://ddragon.leagueoflegends.com/cdn/14.20.1/data/en_US/item.json"
        val connection = URL(apiUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val json = JSONObject(response)
        val data = json.getJSONObject("data")

        val items = mutableListOf<ItemModel>()
        for (key in data.keys()) {
            val itemJson = data.getJSONObject(key)
            val item = ItemModel(
                id = key,
                name = itemJson.getString("name"),
                description = itemJson.getString("description"),
                image = ItemImage(itemJson.getJSONObject("image").getString("full")),
                gold = ItemGold(
                    base = itemJson.getJSONObject("gold").getInt("base"),
                    total = itemJson.getJSONObject("gold").getInt("total"),
                    sell = itemJson.getJSONObject("gold").getInt("sell"),
                    purchasable = itemJson.getJSONObject("gold").getBoolean("purchasable")
                ),
                tags = itemJson.getJSONArray("tags").let { array ->
                    List(array.length()) { array.getString(it) }
                }
            )
            items.add(item)
        }
        return items
    }
}
