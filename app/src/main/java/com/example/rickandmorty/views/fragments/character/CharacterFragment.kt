package com.example.rickandmorty.views.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.adapter.CharacterAdapter
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.views.fragments.BaseFragment

class CharacterFragment : BaseFragment() {

    private var mCharacterBinding: FragmentCharacterBinding? = null
    private lateinit var mCharacterAdapter: CharacterAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCharacterBinding = FragmentCharacterBinding.inflate(inflater, container, false)
        val view = mCharacterBinding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    /*
    * Initializing views.
    * */
    fun init() {

        context?.let {
            mCharacterAdapter = CharacterAdapter(it)
            mGridLayoutManager = GridLayoutManager(it, 2, GridLayoutManager.VERTICAL, false)
        }

        mCharacterBinding?.let {
            it.recyclerCharacters.layoutManager = mGridLayoutManager
            it.recyclerCharacters.adapter = mCharacterAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mCharacterBinding = null
    }
}