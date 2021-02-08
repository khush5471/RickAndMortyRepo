package com.example.rickandmorty.network

import android.util.Log
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/*
* This is the base repository which will be estended by all the repository and by using the method safeApiCall
* the can start the request for data.
* */
abstract class BaseRepository {

    var job: CompletableJob? = null

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResult<T> {


        return withContext(Dispatchers.IO) {
            try {

                NetworkResult.Success(apiCall.invoke())
            } catch (e: Throwable) {

                when (e) {

                    is HttpException -> {
                        Log.e("ERROR", "From APIs")
                        NetworkResult.Failure(false, e.code(), e.response()?.errorBody(), null)
                    }

                    else -> {
                        Log.e("ERROR ", "from network connections.")
                        /*
                        * If exception have their coustom message then show that otherwise so custom message.
                        * */
                        if (!e.message.isNullOrEmpty()) {
                            NetworkResult.Failure(true, null, null, e.message)
                        } else {
                            NetworkResult.Failure(true, null, null, null)
                        }
                    }

                }
            }
        }
    }
}