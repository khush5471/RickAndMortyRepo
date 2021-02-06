package com.example.rickandmorty.views.fragments.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    var mSearchList = MutableLiveData<CharacterListResponse>()
    var mApiError = MutableLiveData<ApiError>()


    /*Fetch Search list list*/
    fun getSearchedData(page: Int, searchKey: String) {


        repository.cancelPreviousRequest()
        repository.searchCharacterByName(
            page,
            searchKey,
            { mSearchList.value = it },
            { mApiError.value = it })
    }

    /*For cancelling request*/
    fun cancelRequest() {
        repository.cancelPreviousRequest()
    }
}