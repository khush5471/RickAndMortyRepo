package com.example.rickandmorty.views.fragments.location

import com.example.rickandmorty.network.WebService
import javax.inject.Inject

class LocationRepository @Inject constructor(private val mSerVice: WebService) {


    suspend fun getLocationList(page: Int) = mSerVice.getLocationList(page)
}