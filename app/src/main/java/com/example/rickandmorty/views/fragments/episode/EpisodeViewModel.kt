package com.example.rickandmorty.views.fragments.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : ViewModel() {


    private val _mEpisodeData: MutableLiveData<NetworkResult<EpisodeListResponse>> =
        MutableLiveData()
    val mEpisodeData: LiveData<NetworkResult<EpisodeListResponse>> get() = _mEpisodeData

    /*Fetch Episode list*/
    fun getEpisodeList(page: Int) = viewModelScope.launch {
        _mEpisodeData.value = repository.getEpisodeLists(page)
    }
}