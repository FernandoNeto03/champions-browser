package com.example.lol_champions_browser.networking

import android.content.Context
import android.util.Log
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.model.ChampionStatsModel
import com.example.lol_champions_browser.viewmodel.NotificationViewModel
import com.google.gson.Gson
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RemoteApi(private val context: Context) {

    private val BASE_URL = "http://girardon.com.br:3001/champions"
    private val TAG = "com.example.lol_champions_browser.networking.RemoteApi"
    private val PREFS_NAME = "ChampionPrefs"
    private val CHAMPION_DATA_KEY = "championData"
    private val TIMESTAMP_KEY = "timestamp"
    private val notificationViewModel = NotificationViewModel(context)

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }

    fun getAllChampions(): List<ChampionModel> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val lastSavedTime = sharedPreferences.getLong(TIMESTAMP_KEY, 0)
        val currentTime = System.currentTimeMillis()
        val lastSavedTimeFormatted = formatTimestamp(lastSavedTime)
        val currentTimeFormatted = formatTimestamp(currentTime)
        val twoWeeksInMillis = 14 * 24 * 60 * 60 * 1000
        Log.d(TAG, "Last saved time: $lastSavedTimeFormatted, Current time: $currentTimeFormatted")

        if (currentTime - lastSavedTime < twoWeeksInMillis) {
            val savedChampionData = sharedPreferences.getString(CHAMPION_DATA_KEY, null)
            if (savedChampionData != null) {
                notificationViewModel.showBasicNotification() //Usando dados salvos localmente
                return Gson().fromJson(savedChampionData, Array<ChampionModel>::class.java).toList()
            } else {
                Log.d(TAG, "Nenhum dado encontrado no SharedPreferences")
            }
        }

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
                }
            }

            val editor = sharedPreferences.edit()
            editor.putString(CHAMPION_DATA_KEY, Gson().toJson(championList))
            editor.putLong(TIMESTAMP_KEY, System.currentTimeMillis())
            editor.apply()

        } catch (e: Exception) {
            Log.d(TAG, "In_Error ${e.localizedMessage}")
        }

        return championList
    }


    fun getChampionsByTag(tag: String): List<ChampionModel> {
        val allChampions = getAllChampions()
        return allChampions.filter { champion ->
            champion.tags.contains(tag)
        }
    }
}
