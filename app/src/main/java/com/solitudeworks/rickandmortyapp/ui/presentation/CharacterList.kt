package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.utils.ErrorItem
import com.solitudeworks.rickandmortyapp.utils.LoadingItem
import com.solitudeworks.rickandmortyapp.utils.LoadingView
import com.solitudeworks.rickandmortyapp.utils.RickAndMortyScreenTitles
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterList(navController: NavHostController) {
    val charactersViewModel = hiltViewModel<CharacterListViewModel>()
    val characterFlow: Flow<PagingData<CharacterDetail>> = charactersViewModel.getPagedCharacterList
    val lazyCharacters: LazyPagingItems<CharacterDetail> = characterFlow.collectAsLazyPagingItems()

    PaddingValues(top = 32.dp)
    LazyColumn {
        items(lazyCharacters.itemCount) { index ->
            CharacterItem(lazyCharacters[index]!!, navController)
        }

        lazyCharacters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = lazyCharacters.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            error.error.localizedMessage ?: "Unknown error",
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyCharacters.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            error.error.localizedMessage ?: "Unknown error",
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: CharacterDetail, navController: NavHostController) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(RickAndMortyScreenTitles.SingleCharacter.name + "/${character.id}/${character.name}")
            }, horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {

        // Character's image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .crossfade(1000)
                .build(), contentDescription = null, modifier = Modifier
                .size(120.dp, 120.dp), error = painterResource(id = R.drawable.no_internet)
        )

        // Character's list entry details
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                // Character's status feedback icon
                Icon(
                    painter = painterResource(
                        id = when (character.status) {
                            "Alive" -> R.drawable.ic_dot_green
                            "Dead" -> R.drawable.ic_dot_red
                            "unknown" -> R.drawable.ic_dot_grey
                            else -> R.drawable.ic_dot_grey
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                // Character's name
                Text(
                    text = character.name ?: stringResource(id = R.string.text_unknown),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Character's details
            Text(text = "Tap for details")

        }

    }

}

@Composable
fun CharacterItemPreview() {
    Column {
        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

        CharacterItem(
            character = CharacterDetail(
                null,
                null,
                null,
                1,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null,
                "Rick",
                null,
                "Human",
                null,
                null,
                null
            ), rememberNavController()
        )

    }
}
