package com.example.akiraito.codechallenge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val genreIds: List<Int>?,
    val budget: Int?,
    val genres: List<Genres>?,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanies>?,
    val productionCountries: List<ProductionCountries>?,
    val releaseDate: String,
    val revenue: Int?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguages>?,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Float?,
    val voteCount: Int?
) : Parcelable