package com.solitudeworks.rickandmortyapp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solitudeworks.rickandmortyapp.data.domain.CharacterListPagingSource
import com.solitudeworks.rickandmortyapp.data.domain.GetPagedCharactersUseCase
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val getPagedCharactersUseCase: GetPagedCharactersUseCase) :
    ViewModel() {

    val getPagedCharacterList: Flow<PagingData<CharacterDetail>> = Pager(
        config = PagingConfig(pageSize = 25),
        initialKey = null,
        pagingSourceFactory = { CharacterListPagingSource(getPagedCharactersUseCase) }
    ).flow.cachedIn(viewModelScope)

}
