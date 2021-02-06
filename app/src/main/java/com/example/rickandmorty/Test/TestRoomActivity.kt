package com.example.rickandmorty.Test

import android.os.Bundle
import com.example.rickandmorty.R
import com.example.rickandmorty.views.activities.BaseActivity

class TestRoomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        addFragment(RoomTestFragment(), false)
    }
}