package com.example.akiraito.codechallenge.domain.repository

import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Movie

interface SearchRepository {
    /**
     * フリーワード検索
     */
    suspend fun searchFwMovie(freeWordText: String): ResultStatus<List<Movie>>
}