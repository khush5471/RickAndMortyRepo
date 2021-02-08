package com.example.rickandmorty.views.fragments.location

import com.example.rickandmorty.network.BaseRepository
import com.example.rickandmorty.network.WebService
import javax.inject.Inject

class LocationRepository @Inject constructor(private val mSerVice: WebService) : BaseRepository() {


    /*
    * Get location list from the api.
    * */
//    fun getLocationList(
//        page: Int,
//        successHandler: (LocationListResponse) -> Unit,
//        errorHandler: (ApiError) -> Unit
//    ) {
//        mSerVice.getLocationList(page).enqueue(object : Callback<LocationListResponse> {
//
//
//            override fun onResponse(
//                call: Call<LocationListResponse>,
//                response: Response<LocationListResponse>
//            ) {
//
//                response.body()?.let {
//                    successHandler(it)
//                } ?: Utils.handleErrorResponse(response, errorHandler)
//            }
//
//            override fun onFailure(call: Call<LocationListResponse>, t: Throwable) {
//                t.message?.let {
//                    errorHandler(ApiError(it))
//
//                } ?: errorHandler(ApiError("Connection failure, Something went wrong"))
//
//
//            }
//
//        })
//    }

    /*
    * Get location list from the api.
    * */
    suspend fun getLocationList(
        page: Int
    ) = safeApiCall { mSerVice.getLocationList(page) }
}