package com.example.rickandmorty.views.activities

import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.rickandmorty.R
import com.example.rickandmorty.views.fragments.BaseFragment

/*
* BaseActivity is used so that it can be extended by all activities and common methods are putted here
* when can be inherited by all its child classes.
* */
open class BaseActivity : AppCompatActivity() {

    /*
    * Add fragment in stack*/
    fun addFragment(fragment: BaseFragment, addBackStack: Boolean) {
        val tag: String = fragment::class.java.simpleName
        val supportFragmentManager = supportFragmentManager
        val transaction: FragmentTransaction? = supportFragmentManager.beginTransaction()
        if (addBackStack) {
            transaction?.addToBackStack(tag)
        }
        transaction?.add(R.id.container, fragment, tag)?.commitAllowingStateLoss()
    }

    /*
    * Disables the view pager swipe.*/
    fun disableViewPagerSwipe(viewPager: ViewPager) {
        viewPager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }

        })
    }
}