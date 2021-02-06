package com.example.rickandmorty.Test

import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TestRoomRepo @Inject constructor(private val mService: WebService) {


    fun getCharList(page: Int) {

        mService.getCharacterList(page).enqueue(object : Callback<CharacterListResponse> {
            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {


            }

            override fun onResponse(
                call: Call<CharacterListResponse>,
                response: Response<CharacterListResponse>
            ) {

                if (response.isSuccessful) {

                }
            }

        })
    }

}