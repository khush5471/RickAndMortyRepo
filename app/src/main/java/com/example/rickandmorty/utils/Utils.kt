package com.example.rickandmorty.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
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
}