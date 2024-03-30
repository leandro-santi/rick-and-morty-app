package com.solitudeworks.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solitudeworks.rickandmortyapp.ui.presentation.CharacterList
import com.solitudeworks.rickandmortyapp.ui.presentation.SingleCharacter
import com.solitudeworks.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.solitudeworks.rickandmortyapp.utils.RickAndMortyScreenTitles
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RickAndMortyApp()

                }

            }
        }
    }
}

@Composable
fun RickAndMortyApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val characterName = backStackEntry?.arguments?.getString("characterName", null)
    val currentScreenName = characterName ?: stringResource(
        RickAndMortyScreenTitles.valueOf(
            backStackEntry?.destination?.route ?: RickAndMortyScreenTitles.CharacterList.name
        ).title
    )
    Scaffold(
        topBar = {
            RickAndMortyAppBar(
                currentScreenName,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        innerPadding.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = RickAndMortyScreenTitles.CharacterList.name
        ) {
            composable(route = RickAndMortyScreenTitles.CharacterList.name) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    CharacterList(navController)

                    // Search button
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        onClick = {
                            navController.navigate(
                                RickAndMortyScreenTitles.SearchList.name
                            )
                        }) {
                        Text(text = stringResource(id = R.string.text_search))
                    }
                }
            }

            composable(route = RickAndMortyScreenTitles.SingleCharacter.name + "/{characterId}/{characterName}") { navBackStackEntry ->
                val characterId =
                    navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    characterId?.let {

                        SingleCharacter(characterId)

                    }
                }
            }

            composable(route = RickAndMortyScreenTitles.SearchList.name) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    // Search()

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyAppBar(
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreenTitle) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

