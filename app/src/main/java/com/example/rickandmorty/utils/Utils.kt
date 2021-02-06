package com.example.rickandmorty.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.di.RetrofitBuilderModule
import com.example.rickandmorty.models.Status
import com.example.rickandmorty.network.ApiError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response

object Utils {

    fun downloadImageByGlide(context: Context, url: String?, imageView: ImageView) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
//                    .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .placeholder(placeHolder)
//                    .thumbnail(0.01f)
                .into(imageView)
        } else {
            Log.e("URL", "EMPTY")
        }

    }


    /*
    * Checks network connections*/
    fun isNetworkAvailable(context: Context?): Boolean {
        context?.let {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            if (netInfo != null && netInfo.isConnectedOrConnecting) {
                return true
            }
        }

        return false
    }


    fun handleErrorResponse(
        response: Response<*>?,
        errorHandler: (ApiError) -> Unit
    ) {
        // If there is some validation error
        response?.errorBody()?.let {
            errorHandler(ApiError(handleApiError(it), response.raw().code()))
        } ?: errorHandler(ApiError())
    }

    // Handling error messages returned by Apis
    fun handleApiError(body: ResponseBody?): String {
        val errorConverter: Converter<ResponseBody, Status> =
            RetrofitBuilderModule.getRetrofit(RetrofitBuilderModule.getOkHttpClient())
                .responseBodyConverter(Status::class.java, arrayOfNulls(0))
        try {
            errorConverter.convert(body)?.let {

                val error: Status = it
                return error.message

            }
            return "Api error"
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }


    }
}