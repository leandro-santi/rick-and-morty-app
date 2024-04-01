package com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solitudeworks.rickandmortyapp.data.domain.CharacterSearchListPagingSource
import com.solitudeworks.rickandmortyapp.data.domain.GetPagedSearchCharactersUseCase
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel
    @Inject
    constructor(
        private val getPagedSearchCharactersUseCase: GetPagedSearchCharactersUseCase,
    ) :
    ViewModel() {
        fun getPagedSearchCharacterList(
            name: String,
            filter: String,
        ): Flow<PagingData<CharacterDetail>> {
            return Pager(
                config = PagingConfig(pageSize = 25),
                initialKey = null,
                pagingSourceFactory = {
                    CharacterSearchListPagingSource(
                        getPagedSearchCharactersUseCase,
                        name,
                        filter,
                    )
                },
            ).flow.cachedIn(viewModelScope)
        }
    }
