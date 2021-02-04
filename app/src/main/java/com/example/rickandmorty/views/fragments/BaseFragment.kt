package com.example.rickandmorty.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
}