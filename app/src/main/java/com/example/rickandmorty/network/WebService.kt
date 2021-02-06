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


    @GET("character")
    suspend fun getCharacterLists(
        @Query("page") page: Int
    ): CharacterListResponse

    @GET("location")
    suspend fun getLocationList(
        @Query("page") page: Int
    ): LocationListResponse

    @GET("episode")
    suspend fun getEpisodeList(
        @Query("page") page: Int
    ): EpisodeListResponse
}