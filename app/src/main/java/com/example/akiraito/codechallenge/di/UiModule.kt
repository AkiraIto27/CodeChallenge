package com.example.akiraito.codechallenge.di

import com.example.akiraito.codechallenge.di.PreferenceModule.NAME_FAVORITE_PREFERENCES
import com.example.akiraito.codechallenge.presentation.detail.MovieDetailViewModel
import com.example.akiraito.codechallenge.presentation.favorite.FavoriteViewModel
import com.example.akiraito.codechallenge.presentation.home.HomeViewModel
import com.example.akiraito.codechallenge.presentation.search.SearchViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object UiModule {
    /**
     * ViewModelに関係するモジュール。
     */
    fun getViewModelModule(): Module {
        return module {
            viewModel {
                HomeViewModel(get(), get(), get())
            }
            viewModel {
                SearchViewModel(get(), get())
            }
            viewModel {
                FavoriteViewModel(get(), get(name = (NAME_FAVORITE_PREFERENCES)))
            }
            viewModel {
                MovieDetailViewModel(get(), get(name = (NAME_FAVORITE_PREFERENCES)))
            }
        }
    }
}