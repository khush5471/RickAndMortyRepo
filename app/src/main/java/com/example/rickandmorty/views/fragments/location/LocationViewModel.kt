package com.example.rickandmorty.views.fragments.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.LocationListResponse
import com.example.rickandmorty.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {


    private val _mLocationData: MutableLiveData<NetworkResult<LocationListResponse>> =
        MutableLiveData()
    val mLocationData: LiveData<NetworkResult<LocationListResponse>> get() = _mLocationData

    /*Fetch location list*/
    fun getLoactionList(page: Int) = viewModelScope.launch {
        _mLocationData.value = repository.getLocationList(page)
    }


}