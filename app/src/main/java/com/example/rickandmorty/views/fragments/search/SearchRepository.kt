package com.example.rickandmorty.views.fragments.search

import android.util.Log
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.ApiError
import com.example.rickandmorty.network.WebService
import com.example.rickandmorty.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val mSerVice: WebService) {

    private var mSearchRequest: Call<CharacterListResponse>? = null


    fun searchCharacterByName(
        page: Int,
        keyword: String,
        successHandler: (CharacterListResponse) -> Unit,
        errorHandler: (ApiError) -> Unit
    ) {
        mSearchRequest = mSerVice.searchCharacterByName(page, keyword)

        mSearchRequest?.enqueue(object : Callback<CharacterListResponse> {

            override fun onResponse(
                call: Call<CharacterListResponse>,
                response: Response<CharacterListResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e("ERRO CODES", " HANDL " + response.code() + " ++ " + response.message())
                }
                response.body()?.let {
                    successHandler(it)
                } ?: Utils.handleErrorResponse(response, errorHandler)
            }

            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {

                errorHandler(ApiError("Connection failure, Something went wrong"))

            }
        })
    }

    /*
    * Cancel the previous request, so that new data result will be shown*/
    fun cancelPreviousRequest() {
        mSearchRequest?.cancel()
    }
}