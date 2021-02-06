package com.example.rickandmorty.views.fragments.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.network.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : ViewModel() {

    var mEpisodeData = MutableLiveData<EpisodeListResponse>()
    var mApiError = MutableLiveData<ApiError>()

    /*Fetch Episode list*/
    fun getEpisodeList(page: Int) {
        repository.getEpisodeLists(page,
            { mEpisodeData.value = it },
            { mApiError.value = it })
    }
}