package com.solitudeworks.rickandmortyapp.data.repository

import com.solitudeworks.rickandmortyapp.data.remote.CharactersApi
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: CharactersApi
) {

    suspend fun getCharactersList(page: Int): Any {
        val response = try {
            api.getCharactersList(page = page)
        } catch (e: Exception) {
            println("Error!")
        }

        return response
    }

    suspend fun getCharacterDetails(characterId: Int): Any {
        val response = try {
            api.getCharacterDetails(id = characterId)
        } catch (e: Exception) {
            println("Error!")
        }
        return response
    }

}