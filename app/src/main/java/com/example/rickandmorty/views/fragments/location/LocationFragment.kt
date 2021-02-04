package com.example.rickandmorty.views.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class LocationFragment : BaseFragment() {

    private var mLocationBinding: FragmentLocationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mLocationBinding = FragmentLocationBinding.inflate(inflater, container, false)
        val view = mLocationBinding?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //destroying view
        mLocationBinding = null
    }
}