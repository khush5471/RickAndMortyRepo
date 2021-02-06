package com.example.rickandmorty.views.fragments.episode

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.models.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : MyViewModel() {

    var mEpisodeData = MutableLiveData<EpisodeListResponse>()

    /*Fetch Episode list*/
    fun getEpisodeList(page: Int) {
        repository.getEpisodeLists(page,
            { mEpisodeData.value = it },
            { mApiError.value = it })
    }
}