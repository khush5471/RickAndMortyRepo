package com.example.rickandmorty

import android.os.Bundle
import com.example.rickandmorty.databinding.ActivityDashboardBinding
import com.example.rickandmorty.views.activities.BaseActivity

class DashBoardActivity : BaseActivity() {

    private lateinit var mDashboardBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = mDashboardBinding.root
        setContentView(view)
    }
}