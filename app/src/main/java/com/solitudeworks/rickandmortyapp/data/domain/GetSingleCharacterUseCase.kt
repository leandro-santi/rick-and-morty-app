package com.solitudeworks.rickandmortyapp.data.domain

import com.solitudeworks.rickandmortyapp.data.repository.CharactersRepository
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import javax.inject.Inject

class GetSingleCharactersUseCase @Inject constructor(
    private val charactersListRepository: CharactersRepository
) {
    suspend fun getSingleCharacter(characterId: Int): CharacterDetail {
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
            null
        )
    }
}
