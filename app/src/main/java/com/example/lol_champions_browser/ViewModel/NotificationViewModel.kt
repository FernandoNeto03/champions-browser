package com.example.lol_champions_browser.viewmodel

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.lol_champions_browser.R
import kotlin.random.Random


class NotificationViewModel(
    private val context: Context
){

    private val notificatioManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification() {
        val notification = NotificationCompat.Builder(context,"request_warning")
            .setContentTitle("Dados Locais")
            .setContentText("Usando dados salvos localmente!")
            .setSmallIcon(R.drawable.folder_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificatioManager.notify(
            Random.nextInt(),
            notification
        )
    }
}