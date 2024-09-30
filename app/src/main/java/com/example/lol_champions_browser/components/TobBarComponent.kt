package com.example.lol_champions_browser.components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.ui.theme.SuperBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(text: String) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text,
                    color = GoldLol,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressedDispatcher?.onBackPressed()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = GoldLol
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
           containerColor = SuperBlue
        ),
        modifier = Modifier.statusBarsPadding()
    )
}
