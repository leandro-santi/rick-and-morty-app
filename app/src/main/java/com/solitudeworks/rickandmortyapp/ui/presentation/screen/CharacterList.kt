package com.solitudeworks.rickandmortyapp.ui.presentation.screen

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
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.CharacterItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.ErrorItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.LoadingItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.LoadingView
import com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel.CharacterListViewModel
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
                            "Internet Error!",
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyCharacters.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            "Error!",
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }

}
