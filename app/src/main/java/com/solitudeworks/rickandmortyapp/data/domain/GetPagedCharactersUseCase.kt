package com.solitudeworks.rickandmortyapp.data.domain

import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.repository.CharactersRepository
import javax.inject.Inject

class GetPagedCharactersUseCase
    @Inject
    constructor(
        private val charactersListRepository: CharactersRepository,
    ) {
        suspend fun getPagedCharacterList(page: Int): List<CharacterDetail> {
            return charactersListRepository.getCharacterList(page).body()?.results
                ?: emptyList<CharacterDetail>()
        }
    }
