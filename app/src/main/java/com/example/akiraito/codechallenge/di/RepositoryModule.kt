package com.example.akiraito.codechallenge.di

import com.example.akiraito.codechallenge.data.repository.DiscoverRepositoryImpl
import com.example.akiraito.codechallenge.data.repository.GenresRepositoryImpl
import com.example.akiraito.codechallenge.data.repository.MovieRepositoryImpl
import com.example.akiraito.codechallenge.data.repository.SearchRepositoryImpl
import com.example.akiraito.codechallenge.domain.repository.DiscoverRepository
import com.example.akiraito.codechallenge.domain.repository.GenresRepository
import com.example.akiraito.codechallenge.domain.repository.MovieRepository
import com.example.akiraito.codechallenge.domain.repository.SearchRepository
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object RepositoryModule {
    fun getRepositoryModule(): Module {
        return module {
            single<MovieRepository> { MovieRepositoryImpl(get()) }
            single<GenresRepository> { GenresRepositoryImpl(get()) }
            single<DiscoverRepository> { DiscoverRepositoryImpl(get()) }
            single<SearchRepository> { SearchRepositoryImpl(get()) }
        }
    }
}