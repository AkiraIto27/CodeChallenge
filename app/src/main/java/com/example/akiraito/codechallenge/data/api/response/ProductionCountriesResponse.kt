package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.ProductionCountries

data class ProductionCountriesResponse(
    val iso_3166_1: String,
    val name: String
) {
    fun toProductionCountries() = ProductionCountries(iso_3166_1, name)
}