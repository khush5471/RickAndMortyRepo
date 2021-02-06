package com.example.rickandmorty.views.fragments.location

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.models.LocationListResponse
import com.example.rickandmorty.models.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : MyViewModel() {

    var mLocationData = MutableLiveData<LocationListResponse>()

    /*Fetch location list*/
    fun getLoactionList(page: Int) {
        repository.getLocationList(page, { mLocationData.value = it }, { mApiError.value = it })
    }


}