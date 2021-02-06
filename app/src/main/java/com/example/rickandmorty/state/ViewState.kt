package com.example.rickandmorty.state

import com.example.rickandmorty.network.ApiError

sealed class ViewState<out T> {
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val exception: Exception) : ViewState<Nothing>()
    data class GenericError(val code: Int? = null, val error: ApiError? = null) :
        ViewState<Nothing>()

    object NetworkError : ViewState<Nothing>()
}