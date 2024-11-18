package com.example.lol_champions_browser.data

import com.example.lol_champions_browser.model.ItemGold
import com.example.lol_champions_browser.model.ItemImage
import com.example.lol_champions_browser.model.ItemModel
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ItemRepositoryImpl : ItemRepository {
    override suspend fun fetchItems(page: Int): List<ItemModel> {
        val apiUrl = "http://girardon.com.br:3001/items?page=$page"
        val connection = URL(apiUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val itemsArray = JSONArray(response)

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
                tags = listOf() // Se precisar de tags, modifique aqui
            )
            items.add(item)
        }
        return items
    }
}
