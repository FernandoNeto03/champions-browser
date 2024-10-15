import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.ViewModel.ItemViewModel
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.ui.theme.FeraDemais
import com.example.lol_champions_browser.ui.theme.GoldLol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

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

@Composable
fun AllItemsActivity(navController: NavHostController, viewModel: ItemViewModel = viewModel()) {
    val itemList by viewModel.items
    val isLoading by viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    Scaffold(
        topBar = {
            TopBarComponent("Voltar")
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(itemList) { item ->
                            var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

                            LaunchedEffect(item.image.full) {
                                imageBitmap = withContext(Dispatchers.IO) {
                                    loadImageFromUrl("https://ddragon.leagueoflegends.com/cdn/14.20.1/img/item/${item.image.full}")
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .border(width = 2.dp, color = FeraDemais)
                                    .clickable {
                                        viewModel.selectItem(item)
                                        navController.navigate("itemDetail")
                                    },
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    imageBitmap?.let {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.Bottom
                                    ) {
                                        Text(
                                            text = item.name,
                                            color = GoldLol,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Preço: ${item.gold.total}",
                                            color = GoldLol,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = item.tags.joinToString(", "),
                                            color = GoldLol,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
