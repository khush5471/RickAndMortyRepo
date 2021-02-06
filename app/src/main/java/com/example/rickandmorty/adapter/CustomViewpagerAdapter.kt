package com.example.rickandmorty.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/*
* Handles the viewpager fragments transition.
* */
class CustomViewpagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    /*
    * For adding fragments in view pager*/
    fun addFragments(fragment: Fragment) {
        mFragmentList.add(fragment)
    }
}