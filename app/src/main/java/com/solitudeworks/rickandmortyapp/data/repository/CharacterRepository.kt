package com.solitudeworks.rickandmortyapp.data.repository

import com.solitudeworks.rickandmortyapp.data.remote.CharactersApi
import com.solitudeworks.rickandmortyapp.data.response.CharacterDetail
import com.solitudeworks.rickandmortyapp.data.response.CharacterList
import com.solitudeworks.rickandmortyapp.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: CharactersApi
) {

    suspend fun getCharactersList(page: Int): Resource<CharacterList> =
        withContext(Dispatchers.IO) {
            val response = try {
                api.getCharactersList(page = page)
            } catch (e: Exception) {
                return@withContext Resource.Error(e.localizedMessage ?: "An error occurred")
            }
            return@withContext Resource.Success(response)
        }

    suspend fun getCharacterDetails(characterId: Int): Resource<CharacterDetail> =
        withContext(Dispatchers.IO) {
            val response = try {
                api.getCharacterDetails(id = characterId)
            } catch (e: Exception) {
                return@withContext Resource.Error(e.localizedMessage ?: "An error occurred")
            }
            return@withContext Resource.Success(response)
        }

}