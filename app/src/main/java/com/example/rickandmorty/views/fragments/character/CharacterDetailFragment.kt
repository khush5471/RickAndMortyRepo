package com.example.rickandmorty.views.fragments.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.BaseFragment

/*
* Shows the character data from character list and search character lsit.
* */
class CharacterDetailFragment : BaseFragment() {


    private var mBinding: FragmentCharacterDetailBinding? = null
    private var mCharacterData: CharacterItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mCharacterData = it.getParcelable(Constants.PARCEL_DATA)
            mBinding?.txtCharName?.text = mCharacterData?.name
            setDataInViews()
        }

        mBinding?.imgBack?.setOnClickListener { clickHandlers(it) }

    }

    /*
    * Setting data from bundle into views*/
    private fun setDataInViews() {

        mBinding?.txtCharName?.text = mCharacterData?.name
        mBinding?.txtCharSpecies?.text = mCharacterData?.species
        mBinding?.txtGender?.text = mCharacterData?.gender
        mBinding?.txtStatus?.text = mCharacterData?.status
        mBinding?.txtCharLocation?.text = mCharacterData?.location?.name
        mBinding?.txtCharOrigin?.text = mCharacterData?.origin?.name
        context?.apply {
            mBinding?.let {
                Utils.downloadImageByGlide(
                    this, mCharacterData?.image, it.imgCharacter,
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_grey
                    )
                )
            }
        }
    }

    /*
    * Handles the click events*/
    private fun clickHandlers(view: View) {
        when (view.id) {
            R.id.imgBack -> {
                activity?.onBackPressed()
            }
        }
    }
}