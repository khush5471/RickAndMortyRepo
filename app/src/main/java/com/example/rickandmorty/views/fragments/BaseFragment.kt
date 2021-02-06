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


open class BaseFragment : Fragment() {


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