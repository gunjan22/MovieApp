package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    val id: Int,
    @field:SerializedName("logo_path")
    val logoPath: Any,
    val name: String,
    @field:SerializedName("origin_country")
    val originCountry: String
)