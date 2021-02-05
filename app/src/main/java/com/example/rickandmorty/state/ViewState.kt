package com.example.rickandmorty.state

sealed class ViewState<out T> {
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val exception: Exception) : ViewState<Nothing>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ViewState<Nothing>()

    object NetworkError : ViewState<Nothing>()
}