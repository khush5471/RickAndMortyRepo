package com.example.rickandmorty.views.activities.holder

import android.os.Bundle
import com.example.rickandmorty.R
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.views.activities.BaseActivity
import com.example.rickandmorty.views.fragments.character.CharacterDetailFragment
import com.example.rickandmorty.views.fragments.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint


/*
* This activities handles is used for handling fragment transaction
*  request from other activities
* */
@AndroidEntryPoint
class HolderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mBundel = intent.getBundleExtra(Constants.INTENT_EXTRAS)

        redirectToRequestedFragment(mBundel.getInt(Constants.PARCEL_KEY), mBundel)
    }

    /*
    * On basis of payload from the bundle, redirect it to theis respective fragments.
    * */
    fun redirectToRequestedFragment(type: Int, bundle: Bundle) {

        when (type) {

            Constants.CHARCTER_DETAIL -> {
                val fragment = CharacterDetailFragment()
                fragment.arguments = bundle
                addFragment(fragment, false)
            }

            Constants.SEARCH -> {
                val fragment = SearchFragment()
                fragment.arguments = bundle
                addFragment(fragment, false)
            }

        }
    }
}