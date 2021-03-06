package com.example.akiraito.codechallenge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCountries(
    val iso_3166_1: String,
    val name: String
): Parcelable