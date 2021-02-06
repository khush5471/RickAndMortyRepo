package com.example.rickandmorty.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
* Application use to initialize HIlT DI.
* */
@HiltAndroidApp
class RickAndMortyApplication : Application()