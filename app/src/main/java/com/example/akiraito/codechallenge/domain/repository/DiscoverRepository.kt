package com.example.akiraito.codechallenge.domain.repository

import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Genres
import com.example.akiraito.codechallenge.domain.model.Movie

interface DiscoverRepository {
    suspend fun fetchMovie(genre: Genres): ResultStatus<List<Movie>>
}