package com.example.rickandmorty.views.fragments.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.models.CharacterListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
) : ViewModel() {

    val mCharacterData = MutableLiveData<CharacterListResponse>()


    fun getCharcterData(page: Int) {
        repository.getCharacterList(page, {
            mCharacterData.value = it
        })
    }

}