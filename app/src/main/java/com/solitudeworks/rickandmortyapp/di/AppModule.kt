package com.solitudeworks.rickandmortyapp.di

import com.solitudeworks.rickandmortyapp.data.remote.CharactersApi
import com.solitudeworks.rickandmortyapp.data.repository.CharacterRepository
import com.solitudeworks.rickandmortyapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRickMortyApi(): CharactersApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CharactersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRickMortyRepository(api: CharactersApi) = CharacterRepository(api = api)

}
