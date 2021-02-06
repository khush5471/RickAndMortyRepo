package com.example.rickandmorty.views.fragments.character

import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.ApiError
import com.example.rickandmorty.network.WebService
import com.example.rickandmorty.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CharacterRepository @Inject constructor(private val mService: WebService) {

    /*
    * Get list of cahracter from API
    * */
    fun getCharacterList(
        page: Int,
        successHandler: (CharacterListResponse) -> Unit,
        errorHandler: (ApiError) -> Unit,
    ) {
        mService.getCharacterList(page).enqueue(object : Callback<CharacterListResponse> {


            override fun onResponse(
                call: Call<CharacterListResponse>,
                response: Response<CharacterListResponse>
            ) {
                response.body()?.let {
                    successHandler(it)
                } ?: Utils.handleErrorResponse(response, errorHandler)

            }

            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {
                t.message?.let {
                    errorHandler(ApiError(it))
                } ?: errorHandler(ApiError("Connection failure, Something went wrong"))

            }
        })


    }


}

