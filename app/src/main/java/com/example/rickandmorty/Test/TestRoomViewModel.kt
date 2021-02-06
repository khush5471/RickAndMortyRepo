package com.example.rickandmorty.Test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.state.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TestRoomViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: TestRoomRepo
) : ViewModel() {

    val mCharacterData = MutableLiveData<CharacterListResponse>()
    val mApiError = MutableLiveData<ErrorResponse>()

    fun testList(page: Int) {
        repository.getCharList(page)
    }
}