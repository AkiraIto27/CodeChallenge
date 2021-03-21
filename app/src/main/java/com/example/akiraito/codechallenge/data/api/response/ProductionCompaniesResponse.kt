package com.example.akiraito.codechallenge.data.api.response

import com.example.akiraito.codechallenge.domain.model.ProductionCompanies
import com.google.gson.annotations.SerializedName

data class ProductionCompaniesResponse(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
) {
    fun toProductionCompanies() = ProductionCompanies(id, logoPath, name, originCountry)
}