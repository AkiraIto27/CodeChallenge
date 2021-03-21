package com.example.akiraito.codechallenge.data.api

import com.example.akiraito.codechallenge.data.api.response.GenreResponse
import retrofit2.http.GET

interface GenresApi {
    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse
}