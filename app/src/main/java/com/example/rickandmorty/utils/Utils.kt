package com.example.rickandmorty.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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


    /* Shows toast.*/
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}