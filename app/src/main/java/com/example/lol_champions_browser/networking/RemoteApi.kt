package com.example.lol_champions_browser.networking

import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    private val BASE_URL = "http://girardon.com.br:3001/champions"
    val TAG = "RemoteApi"

    fun getChampionsByTag(tag: String): List<String> {
        val championList = mutableListOf<String>()

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
                            championList.add(champion.getString("name"))
                            break
                        }
                    }
                }

                Log.d(TAG, "Mages: $championList")
            }

        } catch (e: Exception) {
            Log.d(TAG, "In_Error ${e.localizedMessage}")
        }

        return championList
    }
}