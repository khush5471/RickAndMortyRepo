package com.example.rickandmorty.views.fragments.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.state.ErrorResponse
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
    val mApiError = MutableLiveData<ErrorResponse>()


    fun getCharacterListData(page: Int) {
        viewModelScope.launch {

            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getCharList(page)
                }
                mCharacterData.value = result
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mApiError.value = ErrorResponse("Something went wrong.", null)
                }
            }


        }

    }

}