package com.example.rickandmorty.views.fragments.search

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.models.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : MyViewModel() {
    var mSearchList = MutableLiveData<CharacterListResponse>()


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