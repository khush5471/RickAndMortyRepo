package com.example.rickandmorty.views.activities.holder

import android.os.Bundle
import com.example.rickandmorty.R
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.views.activities.BaseActivity
import com.example.rickandmorty.views.fragments.character.CharacterDetailFragment

class HolderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mBundel = intent.getBundleExtra(Constants.INTENT_EXTRAS)

        redirectToRequestedFragment(mBundel.getInt(Constants.PARCEL_KEY), mBundel)
    }

    fun redirectToRequestedFragment(type: Int, bundle: Bundle) {

        when (type) {

            Constants.CHARCTER_DETAIL -> {
                val fragment = CharacterDetailFragment()
                fragment.arguments = bundle
                addFragment(fragment, false)
            }

        }
    }
}