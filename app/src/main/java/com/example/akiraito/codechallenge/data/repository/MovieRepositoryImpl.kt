package com.example.akiraito.codechallenge.data.repository

import android.util.Log
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.data.api.MovieApi
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.model.RecommendMovie
import com.example.akiraito.codechallenge.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {
    /**
     * オススメ映画リストを取得
     */
    override suspend fun fetchMovieList(page: Int): ResultStatus<RecommendMovie> {
        return runCatching {
            movieApi.getRecommendMovie(page)
        }.fold(
            onSuccess = {
                val response = it.toRecommendMovie()
                ResultStatus.Success(response)
            },
            onFailure = {
                Log.e(TAG, "getData error: ${it.message}", it)
                ResultStatus.Error(Throwable())
            }
        )
    }

    /**
     * 映画詳細を取得
     */
    override suspend fun fetchMovieDetail(id: Int): ResultStatus<Movie> {
        return runCatching {
            movieApi.getMovieDetail(id)
        }.fold(
            onSuccess = {
                val response = it.toMovie()
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