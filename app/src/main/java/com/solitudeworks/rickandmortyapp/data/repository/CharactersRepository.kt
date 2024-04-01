package com.solitudeworks.rickandmortyapp.data.repository

import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.model.CharacterList
import com.solitudeworks.rickandmortyapp.data.remote.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

class CharactersRepository
    @Inject
    constructor() {
        suspend fun getCharacterList(page: Int): Response<CharacterList> {
            return RetrofitInstance.CharacterApi.getPagedCharactersList(page)
        }

        suspend fun getSearchCharacterList( // https://rickandmortyapi.com/api/character/?name=rick&status=alive
            page: Int,
            name: String,
            status: String,
        ): Response<CharacterList> {
            return RetrofitInstance.CharacterApi.getPagedSearchCharactersList(name, status, page)
        }

        suspend fun getSingleCharacter(characterId: Int): Response<CharacterDetail> {
            return RetrofitInstance.CharacterApi.getSingleCharacter(characterId)
        }
    }
