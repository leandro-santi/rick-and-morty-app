package com.solitudeworks.rickandmortyapp.data.remote

import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.response.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getPagedCharactersList(
        @Query("page") page: Int = 1
    ): Response<CharacterList>

    @GET("character")
    suspend fun getPagedSearchCharactersList(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("page") page: Int = 1
    ): Response<CharacterList>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterDetail>

}
