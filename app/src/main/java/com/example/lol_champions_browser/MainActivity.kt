package com.example.lol_champions_browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.lol_champions_browser.ui.theme.LolchampionsbrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            LolchampionsbrowserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}