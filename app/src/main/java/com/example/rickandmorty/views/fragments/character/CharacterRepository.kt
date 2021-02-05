package com.example.rickandmorty.views.fragments.character

import android.util.Log
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CharacterRepository @Inject constructor(private val mService: WebService) {

    fun getCharacterList(page: Int, responseData: (CharacterListResponse) -> Unit) {
        mService.getCharacterList(page).enqueue(object : Callback<CharacterListResponse> {
            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {
                Log.e("Error Message ", "" + t.message)
            }

            override fun onResponse(
                call: Call<CharacterListResponse>,
                response: Response<CharacterListResponse>
            ) {
                response.body()?.let {
                    responseData(it)
                }
            }

        })


    }

    suspend fun getCharList(page: Int) = mService.getCharacterLists(page)

}

