package com.example.rickandmorty

import android.os.Bundle
import android.view.MenuItem
import com.example.rickandmorty.adapter.CustomViewpagerAdapter
import com.example.rickandmorty.databinding.ActivityDashboardBinding
import com.example.rickandmorty.views.activities.BaseActivity
import com.example.rickandmorty.views.fragments.character.CharacterFragment
import com.example.rickandmorty.views.fragments.episode.EpisodeFragment
import com.example.rickandmorty.views.fragments.location.LocationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


/*
* Dashboard activity handles viewpager and BottomNavigation
*
* NOTE - USING VIEWPAGER SO THAT ALL SCREEN CAN GET DATA AT ONCE AND CAN BE SEEN ON ALL FRAGMENTS
* WITHOUT RELOADING AGAIN AND AGAIN WHILE VISITING.
* */
@AndroidEntryPoint
class DashBoardActivity : BaseActivity() {

    private lateinit var mDashboardBinding: ActivityDashboardBinding
    private lateinit var mCustomViewPagerAdapter: CustomViewpagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = mDashboardBinding.root
        setContentView(view)

        init()
        attachBottomNavigationWithViewPager()
    }


    /*
    * This function is used for initializing and for setting the views.
    * */
    private fun init() {

        mCustomViewPagerAdapter = CustomViewpagerAdapter(supportFragmentManager)
        mCustomViewPagerAdapter.addFragments(CharacterFragment())
        mCustomViewPagerAdapter.addFragments(LocationFragment())
        mCustomViewPagerAdapter.addFragments(EpisodeFragment())
        mDashboardBinding.dashboardViewPager.offscreenPageLimit = 3
        mDashboardBinding.dashboardViewPager.adapter = mCustomViewPagerAdapter
        disableViewPagerSwipe(mDashboardBinding.dashboardViewPager)

    }


    /*
    * This function attach the bottom navigation click with the non-swipable viewpager.
    * */
    private fun attachBottomNavigationWithViewPager() {
        mDashboardBinding.bottomView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.item_character -> {
                        mDashboardBinding.txtHeader.text = getString(R.string.character)
                        mDashboardBinding.dashboardViewPager.setCurrentItem(0, false)
                        return true
                    }

                    R.id.item_location -> {
                        mDashboardBinding.txtHeader.text = getString(R.string.location)
                        mDashboardBinding.dashboardViewPager.setCurrentItem(1, false)
                        return true
                    }

                    R.id.item_episode -> {
                        mDashboardBinding.txtHeader.text = getString(R.string.episode)
                        mDashboardBinding.dashboardViewPager.setCurrentItem(2, false)
                        return true
                    }

                }
                return false
            }

        })
    }
}