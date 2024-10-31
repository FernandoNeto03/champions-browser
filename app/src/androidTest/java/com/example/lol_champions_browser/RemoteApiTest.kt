import android.content.Context
import android.content.SharedPreferences
import com.example.lol_champions_browser.networking.RemoteApi
import com.example.lol_champions_browser.viewmodel.NotificationViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class RemoteApiTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var remoteApi: RemoteApi
    private lateinit var mockWebServer: MockWebServer

    private val PREFS_NAME = "ChampionPrefs"
    private val CHAMPION_DATA_KEY = "championData"
    private val TIMESTAMP_KEY = "timestamp"

    @Before
    fun setUp() {
        try {
            // Mock do Contexto e SharedPreferences
            context = mock(Context::class.java)
            sharedPreferences = mock(SharedPreferences::class.java)
            sharedPreferencesEditor = mock(SharedPreferences.Editor::class.java)

            `when`(context.getSharedPreferences(eq(PREFS_NAME), anyInt())).thenReturn(sharedPreferences)
            `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
            `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor)
            `when`(sharedPreferencesEditor.putLong(anyString(), anyLong())).thenReturn(sharedPreferencesEditor)
            doNothing().`when`(sharedPreferencesEditor).apply()

            // Mock do NotificationViewModel
            notificationViewModel = mock(NotificationViewModel::class.java)

            // Iniciar o MockWebServer
            mockWebServer = MockWebServer()
            mockWebServer.start()

            // Instanciar o RemoteApi com o contexto mockado
            remoteApi = RemoteApi(context)

            // Usar reflexão para alterar o BASE_URL para o MockWebServer
            val field = RemoteApi::class.java.getDeclaredField("BASE_URL")
            field.isAccessible = true
            val newUrl = mockWebServer.url("/champions").toString()
            field.set(remoteApi, newUrl)
            println("BASE_URL foi alterado para: $newUrl")

        } catch (e: Exception) {
            e.printStackTrace()
            fail("Erro no setUp: ${e.message}")
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        if (this::mockWebServer.isInitialized) {
            mockWebServer.shutdown()
        }
    }

    @Test
    fun testGetAllChampions_fetchesFromApiWhenCacheIsExpired() {
        // Configurar um timestamp antigo (mais de duas semanas)
        val oldTimestamp = System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000L)
        `when`(sharedPreferences.getLong(eq(TIMESTAMP_KEY), anyLong())).thenReturn(oldTimestamp)

        // Configurar cache vazio
        `when`(sharedPreferences.getString(eq(CHAMPION_DATA_KEY), isNull())).thenReturn(null)

        // Preparar a resposta mockada da API
        val mockApiResponse = """
            [
                {
                    "id": "2",
                    "key": "Key2",
                    "name": "ChampionFromAPI",
                    "title": "Title2",
                    "tags": ["Tag2"],
                    "icon": "iconUrl2",
                    "sprite": {
                        "url": "spriteUrl2",
                        "x": 0,
                        "y": 0
                    },
                    "description": "Description2",
                    "stats": {
                        "hp": 500,
                        "hpperlevel": 80,
                        "mp": 300,
                        "mpperlevel": 50,
                        "movespeed": 330,
                        "armor": 30.0,
                        "armorperlevel": 3.5,
                        "spellblock": 32.0,
                        "spellblockperlevel": 1.25,
                        "attackrange": 150,
                        "hpregen": 5.5,
                        "hpregenperlevel": 0.5,
                        "mpregen": 7,
                        "mpregenperlevel": 0.7,
                        "crit": 0,
                        "critperlevel": 0,
                        "attackdamage": 60,
                        "attackdamageperlevel": 3,
                        "attackspeedperlevel": 2.5,
                        "attackspeed": 0.625
                    }
                }
            ]
        """.trimIndent()

        // Enfileirar a resposta mockada
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockApiResponse)
        )

        // Chamar o método a ser testado
        val champions = remoteApi.getAllChampions()

        // Verificar que a lista retornada é a da API
        assertNotNull(champions)
        assertEquals(1, champions.size)
        assertEquals("ChampionFromAPI", champions[0].name)

        // Verificar que o cache foi atualizado
        verify(sharedPreferencesEditor).putString(eq(CHAMPION_DATA_KEY), anyString())
        verify(sharedPreferencesEditor).putLong(eq(TIMESTAMP_KEY), anyLong())
        verify(sharedPreferencesEditor).apply()

        // Verificar que a requisição foi feita para o caminho correto
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/champions", recordedRequest.path)
    }
}
