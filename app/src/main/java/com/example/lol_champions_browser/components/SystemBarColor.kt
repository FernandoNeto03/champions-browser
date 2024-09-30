package com.example.lol_champions_browser.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemBarColor(color: Color) {
    val activity = LocalContext.current as Activity
    val window = activity.window

    window.statusBarColor = color.toArgb()

    window.navigationBarColor = color.toArgb()
}