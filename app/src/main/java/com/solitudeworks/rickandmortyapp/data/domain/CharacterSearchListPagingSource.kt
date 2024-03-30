package com.solitudeworks.rickandmortyapp.data.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solitudeworks.rickandmortyapp.R
import com.solitudeworks.rickandmortyapp.RickAndMortyApplication
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail

class CharacterSearchListPagingSource(
    private val getPagedSearchCharactersUseCase: GetPagedSearchCharactersUseCase,
    private val name: String,
    private val status: String
) :
    PagingSource<Int, CharacterDetail>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDetail>): Int =
        RickAndMortyApplication.applicationContext().getString(
            R.string.first_page
        ).toInt()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetail> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            getPagedSearchCharactersUseCase.getPagedSearchCharacterList(page, name, status)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}