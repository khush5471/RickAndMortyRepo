package com.example.rickandmorty.views.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {


    private val _mSearchList: MutableLiveData<NetworkResult<CharacterListResponse>> =
        MutableLiveData()
    val mSearchList: LiveData<NetworkResult<CharacterListResponse>> get() = _mSearchList


    var jobSearch: Job? = null

    /*
    * It is used for for search character list.
    * */
    fun getSearchedData(pages: Int, keys: String) {
        jobSearch?.let {
            cancelSearchJob()
            startJob(pages, keys)
        } ?: startJob(pages, keys)
    }

    /*
    * Method initialize the jobSearch and start coroutine to search for the entered keywords.
    * */
    fun startJob(page: Int, searchKey: String) {
        jobSearch = viewModelScope.launch {
            _mSearchList.value = repository.searchCharacterByName(page, searchKey)
        }
    }

    /*This cancels the search job, It is needed because whenver we enter any new character it will cancel the search request
    * from the preview search words, and by cancelling this we can start new request for new search words.*/
    fun cancelSearchJob() {
        jobSearch?.cancel()
    }
}