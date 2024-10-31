package com.example.lol_champions_browser.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lol_champions_browser.model.ChampionModel

class ChampionsViewModel : ViewModel() {
    var selectedChampion by mutableStateOf<ChampionModel?>(null)
    private var mediaPlayer: MediaPlayer? = null


    fun selectChampion(champion: ChampionModel) {
        selectedChampion = champion
    }

    @SuppressLint("DiscouragedApi")
    fun playChampionSound(context:Context, champion: ChampionModel) {
        mediaPlayer?.let {
            it.stop()
            it.release()
        }

        val audioResId = context.resources.getIdentifier(champion.id, "raw", context.packageName)

        if (audioResId != 0) {
            mediaPlayer = MediaPlayer.create(context, audioResId)
            mediaPlayer?.start()

            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.release()
                mediaPlayer = null
            }

        } else {
            Log.e(
                "TAG",
                "Arquivo de áudio não encontrado para o campeão: ${champion.id}"
            )
        }
    }


}
