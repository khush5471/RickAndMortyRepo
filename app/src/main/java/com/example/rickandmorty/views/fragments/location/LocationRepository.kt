package com.example.rickandmorty.views.fragments.location

import com.example.rickandmorty.models.LocationListResponse
import com.example.rickandmorty.network.ApiError
import com.example.rickandmorty.network.WebService
import com.example.rickandmorty.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LocationRepository @Inject constructor(private val mSerVice: WebService) {

    fun getLocationList(
        page: Int,
        successHandler: (LocationListResponse) -> Unit,
        errorHandler: (ApiError) -> Unit
    ) {
        mSerVice.getLocationList(page).enqueue(object : Callback<LocationListResponse> {


            override fun onResponse(
                call: Call<LocationListResponse>,
                response: Response<LocationListResponse>
            ) {

                response.body()?.let {
                    successHandler(it)
                } ?: Utils.handleErrorResponse(response, errorHandler)
            }

            override fun onFailure(call: Call<LocationListResponse>, t: Throwable) {
                errorHandler(ApiError("Connection failure, Something went wrong"))

            }

        })
    }
}