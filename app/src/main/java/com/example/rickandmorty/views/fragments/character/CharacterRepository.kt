package com.example.rickandmorty.views.fragments.character

import com.example.rickandmorty.network.BaseRepository
import com.example.rickandmorty.network.WebService
import javax.inject.Inject

/*
* Repositotry to handel character related API*/
class CharacterRepository @Inject constructor(private val mService: WebService) : BaseRepository() {

    /*
    * Get list of cahracter from API*/
    suspend fun getCharacterList(
        page: Int
    ) = safeApiCall { mService.getCharacterList(page) }

}

