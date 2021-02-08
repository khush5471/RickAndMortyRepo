package com.example.rickandmorty.views.fragments.episode

import com.example.rickandmorty.network.BaseRepository
import com.example.rickandmorty.network.WebService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val mService: WebService) :
    BaseRepository() {

    /*
    * Function to get list of episodes.
    * */
//    fun getEpisodeLists(
//        page: Int,
//        successHandler: (EpisodeListResponse) -> Unit,
//        errorHandler: (ApiError) -> Unit
//    ) {
//        mService.getEpisodeList(page).enqueue(object : Callback<EpisodeListResponse> {
//
//
//            override fun onResponse(
//                call: Call<EpisodeListResponse>,
//                response: Response<EpisodeListResponse>
//            ) {
//
//                response.body()?.let {
//                    successHandler(it)
//                } ?: Utils.handleErrorResponse(response, errorHandler)
//            }
//
//            override fun onFailure(call: Call<EpisodeListResponse>, t: Throwable) {
//
//                t.message?.let {
//                    errorHandler(ApiError(it))
//
//                } ?: errorHandler(ApiError("Connection failure, Something went wrong"))
//
//
//            }
//        })
//    }

    /*
    * Function to get list of episodes.
    * */
    suspend fun getEpisodeLists(page: Int) = safeApiCall { mService.getEpisodeList(page) }
}