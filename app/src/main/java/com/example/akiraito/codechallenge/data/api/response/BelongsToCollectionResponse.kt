package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.BelongsToCollection
import com.google.gson.annotations.SerializedName

data class BelongsToCollectionResponse(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
) {
    fun toBelongsToCollection() = BelongsToCollection(
        id,
        name,
        posterPath,
        backdropPath
    )
}