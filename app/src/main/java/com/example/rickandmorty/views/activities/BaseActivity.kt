package com.example.rickandmorty.views.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.rickandmorty.R
import com.example.rickandmorty.views.fragments.BaseFragment

open class BaseActivity : AppCompatActivity() {

    fun addFragment(fragment: BaseFragment, addBackStack: Boolean) {
        val tag: String = fragment::class.java.simpleName
        val supportFragmentManager = supportFragmentManager
        val transaction: FragmentTransaction? = supportFragmentManager.beginTransaction()
        if (addBackStack) {
            //   transaction?.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse, R.anim.anim_out_reverse)
            transaction?.addToBackStack(tag)
        }
        transaction?.add(R.id.container, fragment, tag)?.commitAllowingStateLoss()
    }
}