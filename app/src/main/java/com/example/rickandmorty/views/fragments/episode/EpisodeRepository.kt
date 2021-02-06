package com.example.rickandmorty.views.fragments.episode

import com.example.rickandmorty.network.WebService
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val mService: WebService) {

    /*
    * Function to get list of episodes.
    * */
    suspend fun getEpisodeLists(page: Int) = mService.getEpisodeList(page)

}