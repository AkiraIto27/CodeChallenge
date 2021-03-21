package com.example.akiraito.codechallenge.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

object PreferenceModule {
    const val KEY_FAVORITE_PREFERENCES = "favorite_preference"
     const val NAME_FAVORITE_PREFERENCES = "favoriteMovieIdPrefs"

    val preferencesModule = module {
        single(name = NAME_FAVORITE_PREFERENCES) { provideFavoritePreferences(androidApplication()) }
    }

    private fun provideFavoritePreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(KEY_FAVORITE_PREFERENCES, Context.MODE_PRIVATE)
}