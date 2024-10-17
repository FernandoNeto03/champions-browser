import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lol_champions_browser.R
import com.example.lol_champions_browser.components.TopBarComponent
import com.example.lol_champions_browser.model.ChampionModel
import com.example.lol_champions_browser.networking.RemoteApi
import com.example.lol_champions_browser.ui.theme.FeraDemais
import com.example.lol_champions_browser.ui.theme.GoldLol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

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
fun DrawTeamActivity(navController: NavHostController, context: Context) {
    var championList by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var team1 by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var team2 by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        championList = withContext(Dispatchers.IO) {
            RemoteApi(context).getAllChampions()
        }

        if (championList.isNotEmpty()) {
            val shuffledChampions = championList.shuffled(Random)
            team1 = shuffledChampions.take(5)
            team2 = shuffledChampions.drop(5).take(5)
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent("Sorteio de Equipes")
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aram),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Equipe 1",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        color = GoldLol
                    )
                    TeamDisplay(team = team1)

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            shareTeams(context, team1, team2)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Compartilhar Equipes")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Equipe 2",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        color = GoldLol
                    )
                    TeamDisplay(team = team2)
                }
            }
        }
    )
}


@Composable
fun TeamDisplay(team: List<ChampionModel>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            team.take(3).forEach { champion ->
                ChampionCard(champion)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            team.drop(3).forEach { champion ->
                ChampionCard(champion)
            }
        }
    }
}

@Composable
fun ChampionCard(champion: ChampionModel) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(champion.icon) {
        imageBitmap = withContext(Dispatchers.IO) {
            loadImageFromUrl(champion.icon)
        }
    }

    Card(
        modifier = Modifier
            .size(100.dp)
            .border(width = 2.dp, color = FeraDemais)
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
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = champion.name,
                    color = GoldLol,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}

fun shareTeams(context: Context, team1: List<ChampionModel>, team2: List<ChampionModel>) {
    val team1Names = team1.joinToString(", ") { it.name }
    val team2Names = team2.joinToString(", ") { it.name }

    val shareText = """
        Equipe 1: $team1Names
        Equipe 2: $team2Names
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Compartilhar equipes via"))
}
