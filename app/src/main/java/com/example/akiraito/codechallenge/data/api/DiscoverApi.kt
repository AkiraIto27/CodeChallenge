package com.example.akiraito.codechallenge.data.api

import com.example.akiraito.codechallenge.data.api.response.RecommendMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    @GET("discover/movie")
    suspend fun getMovie(
        @Query("with_genres") genres: String
    ): RecommendMovieResponse
}