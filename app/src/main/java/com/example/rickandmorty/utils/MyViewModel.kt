package com.example.rickandmorty.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.network.ApiError

open class MyViewModel : ViewModel() {
    var apiError = MutableLiveData<ApiError>()

    var isLoading = MutableLiveData<Boolean>()

    var mConnectionFailure = MutableLiveData<Throwable>()


}