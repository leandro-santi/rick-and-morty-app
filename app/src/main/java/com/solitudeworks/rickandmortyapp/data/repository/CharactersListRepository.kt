package com.solitudeworks.rickandmortyapp.data.repository

import com.solitudeworks.rickandmortyapp.data.remote.RetrofitInstance
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.response.CharacterList
import retrofit2.Response
import javax.inject.Inject

class CharactersListRepository @Inject constructor() {

    suspend fun getCharacterList(page: Int): Response<CharacterList> {
        return RetrofitInstance.CharacterApi.getPagedCharactersList(page)
    }

    suspend fun getSingleCharacter(characterId: Int): Response<CharacterDetail> {
        return RetrofitInstance.CharacterApi.getSingleCharacter(characterId)
    }

}