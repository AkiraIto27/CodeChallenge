package com.example.akiraito.codechallenge.domain.model

data class RecommendMovie(
    val page: Int,
    val movieList: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)