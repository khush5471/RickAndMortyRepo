package com.example.rickandmorty.network

import com.example.rickandmorty.models.CharacterListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {


    @GET("character")
    fun getCharacterList(
        @Query("page") page: Int
    ): Call<CharacterListResponse>
}