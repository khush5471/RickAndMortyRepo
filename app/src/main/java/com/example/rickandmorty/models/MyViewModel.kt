package com.example.rickandmorty.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.network.ApiError

open class MyViewModel : ViewModel() {
    var mApiError = MutableLiveData<ApiError>()

    var isLoading = MutableLiveData<Boolean>()

    var mConnectionFailure = MutableLiveData<Throwable>()


}