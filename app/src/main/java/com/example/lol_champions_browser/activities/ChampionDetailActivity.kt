package com.example.lol_champions_browser.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.components.SystemBarColor
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.ui.theme.SuperBlue
import com.example.lol_champions_browser.viewmodel.ChampionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ChampionDetailActivity(modifier: Modifier = Modifier, viewModel: ChampionsViewModel) {
    val champion = viewModel.selectedChampion
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val attributes = stringResource(id = R.string.attributes)
    val healthPoints = stringResource(id = R.string.healthPoints)
    val healthPerLevel = stringResource(id = R.string.healthPerLevel)
    val attackDamage = stringResource(id = R.string.attackDamage)
    val attackRange = stringResource(id = R.string.attackRange)
    val attackSpeed = stringResource(id = R.string.attackSpeed)
    val manaPoints = stringResource(id = R.string.manaPoints)
    val abilityPower = stringResource(id = R.string.abilityPower)
    val armor = stringResource(id = R.string.armor)
    val magicResistance = stringResource(id = R.string.magicResistance)
    val critical = stringResource(id = R.string.critical)

    SystemBarColor(SuperBlue)

    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    if (champion != null) {
        LaunchedEffect(champion.icon) {
            imageBitmap = withContext(Dispatchers.IO) {
                loadImageFromUrl(champion.icon)
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(text =  stringResource(id = R.string.championDetails))
        }
    ) { innerPadding ->
        champion?.let { champ ->

            Image(
                painter = painterResource(id = R.drawable.preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .background(Color.LightGray)
                    .border(1.dp, GoldLol, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp)
                            .clickable {
                              viewModel.playChampionSound(context, champion)
                            },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        imageBitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = champ.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLol,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = champ.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = SuperBlue,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                Text(
                    text = champ.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Tags: ${champ.tags.joinToString(", ")}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "$attributes:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.health),
                            contentDescription = "HP Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$healthPoints: ${champ.stats.hp}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hpperlevel),
                            contentDescription = "HP per level Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$healthPerLevel: ${champ.stats.hpPerLevel}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ad),
                            contentDescription = "AD Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$attackDamage: ${champ.stats.attackDamage}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.range),
                            contentDescription = "Range Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$attackRange: ${champ.stats.attackRange}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.`as`),
                            contentDescription = "AD Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$attackSpeed: ${champ.stats.attackSpeed}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ap),
                            contentDescription = "AP Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$abilityPower: ${champ.stats.attackDamage}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana),
                            contentDescription = "MP Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$manaPoints: ${champ.stats.mp}", fontSize = 14.sp)
                    }
                    val movementSpeed = stringResource(id = R.string.movementSpeed)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.movespeed),
                            contentDescription = "MS Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$movementSpeed: ${champ.stats.moveSpeed}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.armor),
                            contentDescription = "Armadura Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$armor: ${champ.stats.armor}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mr),
                            contentDescription = "Bloqueio de Feitiço Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$magicResistance: ${champ.stats.spellBlock}", fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.critchance),
                            contentDescription = "Critic Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$critical: ${champ.stats.crit}", fontSize = 14.sp)
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = R.string.championNotFound),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }
    }
}

private fun loadImageFromUrl(url: String): Bitmap? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream = connection.inputStream
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}
