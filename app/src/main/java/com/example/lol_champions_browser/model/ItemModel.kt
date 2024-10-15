package com.example.lol_champions_browser.model

data class ItemModel(
    val id: String,
    val name: String,
    val description: String,
    val image: ItemImage,
    val gold: ItemGold,
    val tags: List<String>
)

data class ItemImage(
    val full: String
)

data class ItemGold(
    val base: Int,
    val total: Int,
    val sell: Int,
    val purchasable: Boolean
)
