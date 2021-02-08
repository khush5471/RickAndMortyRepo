package com.example.rickandmorty.network

import com.google.gson.annotations.SerializedName

/*
* Use to handle error from API*/
data class Status(
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val message: String
)