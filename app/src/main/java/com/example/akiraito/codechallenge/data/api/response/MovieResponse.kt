package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionResponse?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val budget: Int?,
    val genres: List<GenresResponse>?,
    val homepage: String?,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompaniesResponse>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountriesResponse>?,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int?,
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesResponse>?,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toMovie() = Movie(
        adult,
        backdropPath,
        belongsToCollection?.toBelongsToCollection(),
        genreIds,
        budget,
        genres?.map { it.toGenres() },
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        productionCompanies?.map { it.toProductionCompanies() },
        productionCountries?.map { it.toProductionCountries() },
        releaseDate,
        revenue,
        runtime,
        spokenLanguages?.map { it.toSpokenLanguages() },
        status,
        tagline,
        title,
        video,
        (voteAverage?.toInt()?.div(2))?.toFloat() ?: 0.0f,
        voteCount
    )
}