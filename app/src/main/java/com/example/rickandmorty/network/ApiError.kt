package com.example.rickandmorty.network

data class ApiError(
    var errorMessage: String? = null,
    var errorCode: Int? = null
)