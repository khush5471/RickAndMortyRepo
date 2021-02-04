package com.example.rickandmorty.views.activities.splash

import android.os.Bundle
import com.example.rickandmorty.databinding.ActivitySplashBinding
import com.example.rickandmorty.views.activities.BaseActivity
import com.example.rickandmorty.views.fragments.splash.SplashFragment

/*
* Splash activity for displaying static screen for 3 seconds
* */
class SplashActivity : BaseActivity() {

    private lateinit var mSplashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        val view = mSplashBinding.root
        setContentView(view)

        addFragment(SplashFragment(), false)
    }
}