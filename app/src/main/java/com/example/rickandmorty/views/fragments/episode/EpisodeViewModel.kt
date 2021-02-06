package com.example.rickandmorty.views.fragments.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.EpisodeListResponse
import com.example.rickandmorty.state.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: EpisodeRepository
) : ViewModel() {

    var mEpisodeData = MutableLiveData<EpisodeListResponse>()
    var mApiError = MutableLiveData<ErrorResponse>()

    /*Fetch Episode list*/
    fun getEpisodeList(page: Int) {
        viewModelScope.launch {

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getEpisodeLists(page)
                }
                mEpisodeData.value = result
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mApiError.value = ErrorResponse("Something went wrong.", null)
                }
            }


        }
    }
}