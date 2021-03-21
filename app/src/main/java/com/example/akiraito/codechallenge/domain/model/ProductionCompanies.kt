package com.example.akiraito.codechallenge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompanies(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
): Parcelable