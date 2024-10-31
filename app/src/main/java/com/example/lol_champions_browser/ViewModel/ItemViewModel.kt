    package com.example.lol_champions_browser.viewmodel

    import android.util.Log
    import androidx.compose.runtime.State
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.lol_champions_browser.model.ChampionModel
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

        var currentPage = 1
        private var hasMorePages = true

        fun fetchItems() {
            if (_isLoading.value || !hasMorePages) return
            _isLoading.value = true
            viewModelScope.launch {
                val itemList = withContext(Dispatchers.IO) {
                    fetchItemsFromApi(currentPage)
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

        private suspend fun fetchItemsFromApi(page: Int): List<ItemModel> {
            val apiUrl = "http://girardon.com.br:3001/items?page=$page"
            val connection = URL(apiUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val itemsArray = org.json.JSONArray(response)

            val items = mutableListOf<ItemModel>()
            for (i in 0 until itemsArray.length()) {
                val itemJson = itemsArray.getJSONObject(i)
                val item = ItemModel(
                    id = itemJson.getString("name"),
                    name = itemJson.getString("name"),
                    description = itemJson.getString("description"),
                    image = ItemImage(itemJson.getString("icon")),
                    gold = ItemGold(
                        base = itemJson.getJSONObject("price").getInt("base"),
                        total = itemJson.getJSONObject("price").getInt("total"),
                        sell = itemJson.getJSONObject("price").getInt("sell"),
                        purchasable = itemJson.getBoolean("purchasable")
                    ),
                    tags = listOf()
                )
                items.add(item)
            }
            return items
        }


        fun getItemById(itemId: String): ItemModel? {
            return items.value.find { item -> item.id == itemId }
        }
    }
