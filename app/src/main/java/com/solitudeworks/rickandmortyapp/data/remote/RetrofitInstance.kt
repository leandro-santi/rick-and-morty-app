package com.solitudeworks.rickandmortyapp.data.remote

import com.solitudeworks.rickandmortyapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val CharacterApi: CharactersApi = retrofit.create(CharactersApi::class.java)

}
