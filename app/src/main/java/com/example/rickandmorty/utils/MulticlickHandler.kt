package com.example.rickandmorty.utils

import android.os.SystemClock
import android.util.Log

/*
* This class will prevent for double click of views.
* */
object MulticlickHandler {

    private var mLastClickTime: Long = 800
    fun singleClick(): Boolean {
        val minInterval: Long = 700
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        Log.e("Time", "$elapsedTime")
        return elapsedTime >= minInterval
    }
}