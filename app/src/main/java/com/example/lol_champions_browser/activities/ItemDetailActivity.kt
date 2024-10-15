package com.example.lol_champions_browser.activities

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lol_champions_browser.components.SystemBarColor
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.ui.theme.GoldLol
import com.example.lol_champions_browser.ui.theme.SuperBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.platform.LocalContext
import com.example.lol_champions_browser.ViewModel.ItemViewModel

@Composable
fun ItemDetailActivity(modifier: Modifier = Modifier, viewModel: ItemViewModel = viewModel()) {
    val item = viewModel.selectedItem

    Log.d("ItemDetailActivity", "Item selecionado: ${item?.id ?: "Nenhum item selecionado"}")

    val context = LocalContext.current

    SystemBarColor(SuperBlue)

    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    item?.let { itm ->
        LaunchedEffect(itm.image.full) {
            imageBitmap = withContext(Dispatchers.IO) {
                loadImageFromUrl("https://ddragon.leagueoflegends.com/cdn/14.20.1/img/item/${itm.image.full}")
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(text = "Detalhes do Item")
        }
    ) { innerPadding ->
        item?.let { itm ->

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .background(Color.White)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp),
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
                            text = "Nome: ${itm.name}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLol,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Preço Total: ${itm.gold.total}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = SuperBlue,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                Text(
                    text = itm.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Tags: ${itm.tags.joinToString(", ")}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Detalhes do Preço:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Base: ${itm.gold.base}", fontSize = 14.sp)
                        Text(text = "Vendível por: ${itm.gold.sell}", fontSize = 14.sp)
                        Text(text = "Comprável: ${if (itm.gold.purchasable) "Sim" else "Não"}", fontSize = 14.sp)
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
                    text = "Item não encontrado",
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
