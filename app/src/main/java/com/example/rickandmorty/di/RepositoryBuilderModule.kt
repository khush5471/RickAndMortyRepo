package com.example.rickandmorty.di

import com.example.rickandmorty.network.WebService
import com.example.rickandmorty.views.fragments.character.CharacterRepository
import com.example.rickandmorty.views.fragments.location.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* This hilt module will provide the  instance of repositiries in ViewModle
* */

@InstallIn(SingletonComponent::class)
@Module
object RepositoryBuilderModule {


    @Singleton
    @Provides
    fun instanceCharacterRepository(webService: WebService): CharacterRepository {
        return CharacterRepository(webService)
    }

    @Singleton
    @Provides
    fun instanceLocatioinRepository(webService: WebService): LocationRepository {
        return LocationRepository(webService)
    }
}