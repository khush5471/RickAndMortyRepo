package com.example.rickandmorty.network

import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.models.LocationListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {


    @GET("character")
    fun getCharacterList(
        @Query("page") page: Int
    ): Call<CharacterListResponse>


    @GET("location")
    fun getLocationList(
        @Query("page") page: Int
    ): Call<LocationListResponse>

    @GET("episode")
    fun getEpisodeList(
        @Query("page") page: Int
    ): Call<EpisodeListResponse>

    @GET("character")
    fun searchCharacterByName(
        @Query("page") page: Int,
        @Query("name") name: String?
    ): Call<CharacterListResponse>
}