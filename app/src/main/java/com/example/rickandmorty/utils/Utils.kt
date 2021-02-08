package com.example.rickandmorty.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object Utils {

    fun downloadImageByGlide(
        context: Context,
        url: String?,
        imageView: ImageView,
        placeHolder: Drawable?
    ) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeHolder)
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


}