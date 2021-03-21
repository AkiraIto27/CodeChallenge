package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.Genres

data class GenresResponse(
    val id: Int,
    val name: String
) {
    fun toGenres() = Genres(id, name)
}