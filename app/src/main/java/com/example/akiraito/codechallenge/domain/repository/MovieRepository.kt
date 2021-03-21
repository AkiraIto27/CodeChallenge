package com.example.akiraito.codechallenge.domain.repository

import com.example.akiraito.codechallenge.ResultStatus
import com.example.akiraito.codechallenge.domain.model.Movie
import com.example.akiraito.codechallenge.domain.model.RecommendMovie

interface MovieRepository {
    /**
     * オススメ映画リストを取得
     */
    suspend fun fetchMovieList(page: Int): ResultStatus<RecommendMovie>

    /**
     * 映画詳細を取得
     */
    suspend fun fetchMovieDetail(id: Int): ResultStatus<Movie>
}