package com.example.rickandmorty.state

data class ErrorResponse(
    var errorMessage: String? = null,
    var errorCode: Int? = null
)