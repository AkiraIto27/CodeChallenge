package com.example.akiraito.codechallenge.data.api

import com.example.akiraito.codechallenge.data.api.response.MovieResponse
import com.example.akiraito.codechallenge.data.api.response.RecommendMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getRecommendMovie(
        @Query("page") page: Int
    ): RecommendMovieResponse
}