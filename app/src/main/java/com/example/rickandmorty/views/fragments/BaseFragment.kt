package com.example.rickandmorty.views.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.views.activities.BaseActivity

/*
* Base fragment , use to be extended by all the fragments and contains the common methods
* which can be used by all hchild classes*/
open class BaseFragment : Fragment() {


    /*
    * Use to start the activity from the fragment.
    * */
    fun startActivityFromFragment(
        activityContext: Activity?,
        activity: Class<out BaseActivity>,
        bundle: Bundle?,
        isFadeAnimation: Boolean
    ) {
        activityContext?.let {
            val intent = Intent(activityContext, activity)
            if (bundle != null) {
                intent.putExtra(Constants.INTENT_EXTRAS, bundle)
            }
            activityContext.startActivity(intent)
            if (isFadeAnimation) {
                activityContext.overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            } else {
                activityContext.overridePendingTransition(
                    R.anim.anim_in,
                    R.anim.anim_out
                )
            }
        }
    }

    /*
    * Attach the fragment in stack.
    * */
    fun attachFragment(fragment: BaseFragment, addToBackStack: Boolean) {

        val tag: String = fragment::class.java.simpleName
        activity?.let {
            val manager = it.supportFragmentManager
            val oldFragmentObject = manager.findFragmentByTag(tag)
            val transaction = manager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.anim_in,
                R.anim.anim_out,
                R.anim.anim_in_reverse,
                R.anim.anim_out_reverse
            )
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }

            transaction.add(R.id.container, fragment, tag)
                .commitAllowingStateLoss()
        }


    }

    /* Shows toast.*/
    fun showToast(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /*
    * Hides the keyboard.
    * */
    fun hideKeyBoard(view: View?) {
        val inputManager = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        if (inputManager is InputMethodManager) {
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }


}