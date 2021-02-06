package com.example.rickandmorty.views.fragments.episode

import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.network.ApiError
import com.example.rickandmorty.network.WebService
import com.example.rickandmorty.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val mService: WebService) {

    /*
    * Function to get list of episodes.
    * */
    fun getEpisodeLists(
        page: Int,
        successHandler: (EpisodeListResponse) -> Unit,
        errorHandler: (ApiError) -> Unit
    ) {
        mService.getEpisodeList(page).enqueue(object : Callback<EpisodeListResponse> {


            override fun onResponse(
                call: Call<EpisodeListResponse>,
                response: Response<EpisodeListResponse>
            ) {

                response.body()?.let {
                    successHandler(it)
                } ?: Utils.handleErrorResponse(response, errorHandler)
            }

            override fun onFailure(call: Call<EpisodeListResponse>, t: Throwable) {

                errorHandler(ApiError("Connection failure, Something went wrong"))

            }
        })
    }

}