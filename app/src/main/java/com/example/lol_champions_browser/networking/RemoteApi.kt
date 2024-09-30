package com.example.lol_champions_browser.networking

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    private val BASE_URL = "http://girardon.com.br:3001/champions"
    val TAG = "RemoteApi"

    fun getFact() {
        Thread(Runnable {
            val connection = URL(BASE_URL).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.connectTimeout = 1000
            connection.readTimeout = 1000
            connection.doInput = true


            try {
                val reader = InputStreamReader(connection.inputStream)

                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.forEachLine {
                        response.append(it.trim())
                    }
                    Log.d(TAG,"In_Success ${response.toString()}")
                }

            } catch (e: Exception){
                Log.d(TAG,"In_Error ${e.localizedMessage}")
            }

        }).start()
    }
}