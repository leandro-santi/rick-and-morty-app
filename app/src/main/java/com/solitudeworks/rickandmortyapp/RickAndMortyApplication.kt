package com.solitudeworks.rickandmortyapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickAndMortyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: RickAndMortyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = RickAndMortyApplication.applicationContext()
    }
}
