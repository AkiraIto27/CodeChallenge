package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.RecommendMovie
import com.google.gson.annotations.SerializedName

data class RecommendMovieResponse(
    val page: Int,
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    fun toRecommendMovie() = RecommendMovie(
        page, results.map { it.toMovie() }, totalPages, totalResults
    )
}