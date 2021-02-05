package com.example.rickandmorty.views.fragments.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.CharacterListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun getdd(page: Int) {

        viewModelScope.launch {

            try {
                val ss = withContext(Dispatchers.IO) {
                    repository.getCharList(page)
                }
                mCharacterData.value = ss
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }


        }

    }

}