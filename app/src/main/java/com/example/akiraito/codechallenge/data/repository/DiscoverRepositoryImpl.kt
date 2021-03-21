package com.example.akiraito.codechallenge.data.repository

import android.util.Log
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.data.api.DiscoverApi
import com.example.akiraito.codechallenge.domain.model.Genres
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.DiscoverRepository

class DiscoverRepositoryImpl(private val discoverApi: DiscoverApi) : DiscoverRepository {
    override suspend fun fetchMovie(genre: Genres): ResultStatus<List<Movie>> {
        return runCatching {
            discoverApi.getMovie(genre.id.toString())
        }.fold(
            onSuccess = {
                val response = it.toRecommendMovie()
                ResultStatus.Success(response.movieList)
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