package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.data.model.CharacterListEntry
import com.solitudeworks.rickandmortyapp.ui.theme.RobotoCondensed

@Composable
fun CharacterListScreen(
    navController: NavController
    // viewModel: CharacterListViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.rick_morty),
                    contentDescription = "Rick And Morty Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // viewModel.searchCharacterList(it)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Lógica do primeiro botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Alive")
                }
                Button(
                    onClick = { /* Lógica do segundo botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Dead")
                }
                Button(
                    onClick = { /* Lógica do terceiro botão */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Unknown")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // CharacterList(navController = navController)
        }
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(10.dp))
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun CharacterList(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characterList by remember { viewModel.characterList }
    val isEndOfList by remember { viewModel.isEndOfList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (characterList.size % 2 == 0) {
            characterList.size / 2
        } else {
            characterList.size / 2 + 1
        }

        items(itemCount) {
            if (it >= itemCount - 1 && !isEndOfList && !isLoading && !isSearching) {
                viewModel.loadPaginatedCharacters()
            }
            CharacterRow(rowIndex = it, characters = characterList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()) {
//            RetryLayout(error = loadError) {
//                viewModel.loadPaginatedCharacters()
//            }
            println()
        }
    }
 }


@Composable
fun CharacterEntry(
    character: CharacterListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.background
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "character_detail_screen/${dominantColor.toArgb()}/${character.characterId}"
                )
            }

    ) {
        Column {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .target {
                        viewModel.calculateDominantColor(it) { color ->
                            dominantColor = color
                        }
                    }
                    .build(),
                contentDescription = character.characterName,
                fadeIn = true,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.scale(0.5f)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = character.characterName!!,
                fontFamily = RobotoCondensed,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun CharacterRow(
    rowIndex: Int,
    characters: List<CharacterListEntry>,
    navController: NavController
) {
    Column {
        Row {
            CharacterEntry(
                character = characters[rowIndex * 2],
                navController = navController,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (characters.size >= rowIndex * 2 + 2) {
                CharacterEntry(
                    character = characters[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()

                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
