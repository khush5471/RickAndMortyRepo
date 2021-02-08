package com.example.rickandmorty.views.fragments.search

//import com.example.rickandmorty.state.ErrorUtils.parseError
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.BaseRepository
import com.example.rickandmorty.network.WebService
import retrofit2.Call
import javax.inject.Inject


class SearchRepository @Inject constructor(private val mSerVice: WebService) : BaseRepository() {

    private var mSearchRequest: Call<CharacterListResponse>? = null


    /*
    * Search with character name
    * */
//    fun searchCharacterByName(
//        page: Int,
//        keyword: String,
//        successHandler: (CharacterListResponse) -> Unit,
//        errorHandler: (ApiError) -> Unit
//    ) {
//        mSearchRequest = mSerVice.searchCharacterByName(page, keyword)
//
//        mSearchRequest?.enqueue(object : Callback<CharacterListResponse> {
//
//            override fun onResponse(
//                call: Call<CharacterListResponse>,
//                response: Response<CharacterListResponse>
//            ) {
//                response.body()?.let {
//                    successHandler(it)
//                } ?: Utils.handleErrorResponse(response, errorHandler)
//            }
//
//            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {
//                call.let {
//                    if (!it.isCanceled) {
//                        t.message?.let {
//                            errorHandler(ApiError(it))
//
//                        } ?: errorHandler(ApiError("Connection failure, Something went wrong"))
//                    }
//                }
//            }
//        })
//    }
//
//    /*
//    * Cancel the previous request, so that new data result will be shown*/
//    fun cancelPreviousRequest() {
//        mSearchRequest?.cancel()
//    }

    /*
    * Search with character name
    * */
    suspend fun searchCharacterByName(
        page: Int,
        keyword: String
    ) = safeApiCall {
        mSerVice.searchCharacterByName(page, keyword)
    }
}