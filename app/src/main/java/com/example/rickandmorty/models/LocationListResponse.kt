package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class LocationListResponse(

    @field:SerializedName("results")
    val results: List<ResultsLocationItem?>? = null,

    @field:SerializedName("info")
    val info: Info? = null
)

data class ResultsLocationItem(

    @field:SerializedName("created")
    val created: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("residents")
    val residents: List<String?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("dimension")
    val dimension: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class InfoLocation(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("pages")
    val pages: Int? = null,

    @field:SerializedName("prev")
    val prev: Any? = null,

    @field:SerializedName("count")
    val count: Int? = null
)
