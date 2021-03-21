package com.example.akiraito.codechallenge.domain.repository

import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Genres

interface GenresRepository {
    suspend fun fetchGenres(): ResultStatus<List<Genres>>
}