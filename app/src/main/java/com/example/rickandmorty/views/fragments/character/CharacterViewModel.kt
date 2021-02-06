package com.example.rickandmorty.views.fragments.character

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.ApiError
import com.example.rickandmorty.utils.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : MyViewModel() {

    val mCharacterData = MutableLiveData<CharacterListResponse>()
    val mApiError = MutableLiveData<ApiError>()


    /*
    * Get list of characters*/
    fun getCharacterListData(page: Int) {
        repository.getCharacterList(page, {
            mCharacterData.value = it
        }, { mApiError.value = it })

    }

}