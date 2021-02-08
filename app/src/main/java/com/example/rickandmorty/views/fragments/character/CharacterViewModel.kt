package com.example.rickandmorty.views.fragments.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _mCharacterData: MutableLiveData<NetworkResult<CharacterListResponse>> =
        MutableLiveData()
    val mCharacterData: LiveData<NetworkResult<CharacterListResponse>> get() = _mCharacterData

    /*
    * Get list of characters*/
    fun getCharacterListData(page: Int) = viewModelScope.launch {
        _mCharacterData.value = repository.getCharacterList(page)
    }
}