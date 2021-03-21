package com.example.akiraito.codechallenge.data.repository

import android.util.Log
import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.data.api.SearchApi
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.repository.SearchRepository

class SearchRepositoryImpl(private val searchApi: SearchApi) : SearchRepository {
    /**
     * フリーワード検索
     */
    override suspend fun searchFwMovie(freeWordText: String): ResultStatus<List<Movie>> {
        return runCatching {
            searchApi.getFwMovie(freeWordText)
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