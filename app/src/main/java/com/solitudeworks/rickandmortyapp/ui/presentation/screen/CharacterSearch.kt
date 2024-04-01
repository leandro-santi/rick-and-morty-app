package com.solitudeworks.rickandmortyapp.ui.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.characterItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.errorItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.loadingItem
import com.solitudeworks.rickandmortyapp.ui.presentation.shared.loadingView
import com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel.CharacterSearchViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun characterSearchScreen(navController: NavHostController) {
    var showSearchList by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Name text field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        // Status text field
        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        // Search button
        Button(
            onClick = { showSearchList = true },
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
        ) {
            Text(text = "Search!")
        }

        if (showSearchList) {
            characterSearchList(navController, name, status)
        }
    }
}

@Composable
fun characterSearchList(
    navController: NavHostController,
    name: String,
    filter: String,
) {
    val charactersSearchViewModel = hiltViewModel<CharacterSearchViewModel>()
    val characterFlow: Flow<PagingData<CharacterDetail>> =
        charactersSearchViewModel.getPagedSearchCharacterList(name, filter)
    val lazyCharacters: LazyPagingItems<CharacterDetail> = characterFlow.collectAsLazyPagingItems()

    PaddingValues(top = 32.dp)
    LazyColumn {
        items(lazyCharacters.itemCount) { index ->
            characterItem(lazyCharacters[index]!!, navController)
        }

        lazyCharacters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { loadingView(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { loadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = lazyCharacters.loadState.refresh as LoadState.Error
                    item {
                        errorItem(
                            "Internet Error!",
                            onClickRetry = { retry() },
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyCharacters.loadState.append as LoadState.Error
                    item {
                        errorItem(
                            "Error!",
                            onClickRetry = { retry() },
                        )
                    }
                }
            }
        }
    }
}
