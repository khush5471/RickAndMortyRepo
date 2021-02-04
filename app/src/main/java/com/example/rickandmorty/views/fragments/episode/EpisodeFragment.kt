package com.example.rickandmorty.views.fragments.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentEpisodeBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class EpisodeFragment : BaseFragment() {

    private var mEpisodeBinding: FragmentEpisodeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mEpisodeBinding = FragmentEpisodeBinding.inflate(inflater, container, false)
        val view = mEpisodeBinding?.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        //Destroying viewbinding object
        mEpisodeBinding = null
    }
}