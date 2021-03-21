package com.example.akiraito.codechallenge.data.repository

import android.util.Log
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.data.api.GenresApi
import com.example.akiraito.codechallenge.domain.model.Genres
import com.example.akiraito.codechallenge.domain.repository.GenresRepository

class GenresRepositoryImpl(private val genresApi: GenresApi) : GenresRepository {
    override suspend fun fetchGenres(): ResultStatus<List<Genres>> {
        return runCatching {
            genresApi.getGenres()
        }.fold(
            onSuccess = {
                val response = it.genres.map { it.toGenres() }
                ResultStatus.Success(response)
            },
            onFailure = {
                Log.e(TAG, "getData error: ${it.message}", it)
                ResultStatus.Error(Throwable())
            }
        )
    }

    companion object {
        private val TAG = this::class.java.name
    }
}