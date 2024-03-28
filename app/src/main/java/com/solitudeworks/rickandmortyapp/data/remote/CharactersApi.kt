package com.solitudeworks.rickandmortyapp.data.remote

import com.solitudeworks.rickandmortyapp.data.model.Character
import com.solitudeworks.rickandmortyapp.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getCharactersList(@Query("page") page: Int): Result

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): Character

}