package com.littlelemon.ordering

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.littlelemon.ordering.ui.theme.LittleLemonAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonAppTheme {
                val databaseMenuItems = database
                    .menuItemDao()
                    .getAll()
                    .observeAsState(listOf())
                MyNavigation(applicationContext, databaseMenuItems)

            }
        }
        lifecycleScope.launch(Dispatchers.IO)
        {
            if (database.menuItemDao().isEmpty()) {
                try {
                    val networkResponse = fetchMenu()
                    saveMenuToDatabase(networkResponse)
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed to fetch menu", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response: MenuNetworkData = httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()
        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}


@Composable
fun MyNavigation(context: Context, databaseItems: State<List<MenuItemRoom>>) {
    val navController = rememberNavController()
    Navigation(navController, context, databaseItems)
}