package com.example.rickandmorty.network

import okhttp3.ResponseBody

/*
* Handles the Errors from API*/
sealed class NetworkResult<out T> {

    data class Success<out T : Any>(val data: T) : NetworkResult<T>()

    data class Error(val error: Exception) : NetworkResult<Nothing>()

    data class Failure(
        val isNetworkError: Boolean?,
        val errocode: Int?,
        val errorBody: ResponseBody?,
        val errorMessage: String?
    ) : NetworkResult<Nothing>()
}