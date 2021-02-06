package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val message: String
)