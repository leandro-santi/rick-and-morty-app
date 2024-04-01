package com.solitudeworks.rickandmortyapp.data.domain

import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.repository.CharactersRepository
import javax.inject.Inject

class GetPagedSearchCharactersUseCase
    @Inject
    constructor(
        private val charactersListRepository: CharactersRepository,
    ) {
        suspend fun getPagedSearchCharacterList(
            page: Int,
            name: String,
            status: String,
        ): List<CharacterDetail> {
            return charactersListRepository.getSearchCharacterList(page, name, status).body()?.results
                ?: emptyList<CharacterDetail>()
        }
    }
