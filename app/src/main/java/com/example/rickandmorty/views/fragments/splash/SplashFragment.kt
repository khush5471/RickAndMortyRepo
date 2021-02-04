package com.example.rickandmorty.views.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var mSplashFragmentBinding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}