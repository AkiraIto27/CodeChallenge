package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.SpokenLanguages

data class SpokenLanguagesResponse(
    val iso_639_1: String,
    val name: String
) {
    fun toSpokenLanguages() = SpokenLanguages(iso_639_1, name)
}