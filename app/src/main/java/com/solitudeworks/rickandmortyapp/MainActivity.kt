package com.solitudeworks.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.solitudeworks.rickandmortyapp.ui.presentation.CharacterListScreen
import com.solitudeworks.rickandmortyapp.ui.theme.RickAndMortyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "character_list_screen"
                ) {
                    composable("character_list_screen") {
                        CharacterListScreen(navController = navController)
                    }
                    composable(
                        "character_detail_screen/{dominantColor}/{characterId}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("characterId") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val characterId = remember {
                            it.arguments?.getString("characterId")
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyAppTheme {
        Greeting("Android")
    }
}