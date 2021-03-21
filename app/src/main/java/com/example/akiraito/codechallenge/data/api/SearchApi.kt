package com.example.akiraito.codechallenge.data.api

import com.example.akiraito.codechallenge.data.api.response.RecommendMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/movie")
    suspend fun getFwMovie(
        @Query("query") searchText: String
    ): RecommendMovieResponse
}