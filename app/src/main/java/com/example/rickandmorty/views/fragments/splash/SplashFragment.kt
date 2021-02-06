package com.example.rickandmorty.views.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.DashBoardActivity
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.views.fragments.BaseFragment

/*
* Splash screen just shows splash UI for 3 seconds.*/
class SplashFragment : BaseFragment() {

    private var mSplashFragmentBinding: FragmentSplashBinding? = null
    private val mHandler = Handler()
    private val mRunnable = Runnable { startNextActivity() }


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

        mHandler.postDelayed(mRunnable, 3000)

    }

    /*
    * Starts the next activity after splash
    * */
    private fun startNextActivity() {
        startActivityFromFragment(activity, DashBoardActivity::class.java, null, true)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //setting the viewbinding variable to null
        mSplashFragmentBinding = null
        mHandler.removeCallbacks(
            mRunnable
        )
    }
}