package com.solitudeworks.rickandmortyapp.data.domain

import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.repository.CharactersRepository
import javax.inject.Inject

class GetSingleCharactersUseCase
    @Inject
    constructor(
        private val charactersListRepository: CharactersRepository,
    ) {
        suspend fun getSingleCharacterUseCase(characterId: Int): CharacterDetail {
            return charactersListRepository.getSingleCharacter(characterId).body() ?: CharacterDetail(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
            )
        }
    }
