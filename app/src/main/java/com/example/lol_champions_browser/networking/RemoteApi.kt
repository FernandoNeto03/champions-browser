package com.example.lol_champions_browser.networking

import android.util.Log
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ChampionStatsModel
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    private val BASE_URL = "http://girardon.com.br:3001/champions"
    val TAG = "RemoteApi"

    fun getChampionsByTag(tag: String): List<ChampionModel> {
        val championList = mutableListOf<ChampionModel>()

        try {
            val connection = URL(BASE_URL).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.connectTimeout = 1000
            connection.readTimeout = 1000
            connection.doInput = true

            val reader = InputStreamReader(connection.inputStream)
            reader.use { input ->
                val response = StringBuilder()
                val bufferedReader = BufferedReader(input)

                bufferedReader.forEachLine {
                    response.append(it.trim())
                }

                val championsArray = JSONArray(response.toString())
                for (i in 0 until championsArray.length()) {
                    val champion = championsArray.getJSONObject(i)
                    val tags = champion.getJSONArray("tags")
                    for (j in 0 until tags.length()) {
                        if (tags.getString(j) == tag) {
                            // Extract data and map to ChampionModel
                            val statsJson = champion.getJSONObject("stats")
                            val spriteJson = champion.getJSONObject("sprite")

                            val championObject = ChampionModel(
                                id = champion.getString("id"),
                                key = champion.getString("key"),
                                name = champion.getString("name"),
                                title = champion.getString("title"),
                                tags = (0 until tags.length()).map { tags.getString(it) },
                                icon = champion.getString("icon"),
                                spriteUrl = spriteJson.getString("url"),
                                spriteX = spriteJson.getInt("x"),
                                spriteY = spriteJson.getInt("y"),
                                description = champion.getString("description"),
                                stats = ChampionStatsModel(
                                    hp = statsJson.getInt("hp"),
                                    hpPerLevel = statsJson.getInt("hpperlevel"),
                                    mp = statsJson.getInt("mp"),
                                    mpPerLevel = statsJson.getInt("mpperlevel"),
                                    moveSpeed = statsJson.getInt("movespeed"),
                                    armor = statsJson.getDouble("armor"),
                                    armorPerLevel = statsJson.getDouble("armorperlevel"),
                                    spellBlock = statsJson.getDouble("spellblock"),
                                    spellBlockPerLevel = statsJson.getDouble("spellblockperlevel"),
                                    attackRange = statsJson.getInt("attackrange"),
                                    hpRegen = statsJson.getDouble("hpregen"),
                                    hpRegenPerLevel = statsJson.getDouble("hpregenperlevel"),
                                    mpRegen = statsJson.getInt("mpregen"),
                                    mpRegenPerLevel = statsJson.getInt("mpregenperlevel"),
                                    crit = statsJson.getInt("crit"),
                                    critPerLevel = statsJson.getInt("critperlevel"),
                                    attackDamage = statsJson.getInt("attackdamage"),
                                    attackDamagePerLevel = statsJson.getInt("attackdamageperlevel"),
                                    attackSpeedPerLevel = statsJson.getDouble("attackspeedperlevel"),
                                    attackSpeed = statsJson.getDouble("attackspeed")
                                )
                            )
                            championList.add(championObject)
                            break
                        }
                    }
                }

                Log.d(TAG, "Champions: $championList")
            }

        } catch (e: Exception) {
            Log.d(TAG, "In_Error ${e.localizedMessage}")
        }

        return championList
    }
}