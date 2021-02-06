package com.example.rickandmorty.views.fragments.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.models.LocationListResponse
import com.example.rickandmorty.network.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    var mLocationData = MutableLiveData<LocationListResponse>()
    var mApiError = MutableLiveData<ApiError>()

    /*Fetch location list*/
    fun getLoactionList(page: Int) {
        repository.getLocationList(page, { mLocationData.value = it }, { mApiError.value = it })
    }


}