package com.example.lol_champions_browser

import com.example.lol_champions_browser.model.ItemModel
import com.example.lol_champions_browser.model.ItemGold
import com.example.lol_champions_browser.model.ItemImage
import org.junit.Assert.assertEquals
import org.junit.Test

class ItemModelTest {

    @Test
    fun `ItemModel initializes correctly`() {
        val item = ItemModel(
            id = "1",
            name = "Test Item",
            description = "A test item description",
            image = ItemImage("test_icon"),
            gold = ItemGold(100, 200, 50, true),
            tags = listOf("Tag1", "Tag2")
        )

        assertEquals("1", item.id)
        assertEquals("Test Item", item.name)
        assertEquals("A test item description", item.description)
        assertEquals("test_icon", item.image.full)
        assertEquals(100, item.gold.base)
        assertEquals(true, item.gold.purchasable)
        assertEquals(listOf("Tag1", "Tag2"), item.tags)
    }
}
