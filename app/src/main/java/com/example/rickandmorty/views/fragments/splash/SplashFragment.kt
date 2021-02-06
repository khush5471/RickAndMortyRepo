package com.example.rickandmorty.views.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.DashBoardActivity
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class SplashFragment : BaseFragment() {

    private var mSplashFragmentBinding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mSplashFragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = mSplashFragmentBinding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(object : Runnable {
            override fun run() {
                startActivityFromFragment(activity, DashBoardActivity::class.java, null, true)
//                startActivityFromFragment(activity, TestRoomActivity::class.java, null, true)
                activity?.finish()
            }
        }, 3000)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        //setting the viewbinding variable to null
        mSplashFragmentBinding = null
    }
}