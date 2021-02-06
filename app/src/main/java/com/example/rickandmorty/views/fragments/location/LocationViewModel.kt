package com.example.rickandmorty.views.fragments.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.LocationListResponse
import com.example.rickandmorty.state.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: LocationRepository
) : ViewModel() {

    var mLocationData = MutableLiveData<LocationListResponse>()
    var mApiError = MutableLiveData<ErrorResponse>()

    /*Fetch location list*/
    fun getLoactionList(page: Int) {
        viewModelScope.launch {

            try {
                val ss = withContext(Dispatchers.IO) {
                    repository.getLocationList(page)
                }
                mLocationData.value = ss
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mApiError.value = ErrorResponse("Something went wrong.", null)
                }
            }


        }
    }


}