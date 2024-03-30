package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.utils.ErrorItem
import com.solitudeworks.rickandmortyapp.utils.LoadingItem
import com.solitudeworks.rickandmortyapp.utils.LoadingView
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterSearchList(navController: NavHostController) {
    val charactersSearchViewModel = hiltViewModel<CharacterSearchViewModel>()
    val characterFlow: Flow<PagingData<CharacterDetail>> = charactersSearchViewModel.pagedCharacterList
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
