package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solitudeworks.rickandmortyapp.data.domain.GetPagedSearchCharactersUseCase
import com.solitudeworks.rickandmortyapp.data.domain.CharacterSearchListPagingSource
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(private val getPagedSearchCharactersUseCase: GetPagedSearchCharactersUseCase) :
    ViewModel() {

    private val characterListScope = CoroutineScope(Dispatchers.Main)

    val pagedCharacterList: Flow<PagingData<CharacterDetail>> = Pager(
        config = PagingConfig(pageSize = 25),
        initialKey = null,
        pagingSourceFactory = { CharacterSearchListPagingSource(getPagedSearchCharactersUseCase) }
    ).flow.cachedIn(viewModelScope)

}