package com.example.rickandmorty.views.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class CharacterFragment : BaseFragment() {

    private var mCharacterBinding: FragmentCharacterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCharacterBinding = FragmentCharacterBinding.inflate(inflater, container, false)
        val view = mCharacterBinding?.root
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        mCharacterBinding = null
    }
}