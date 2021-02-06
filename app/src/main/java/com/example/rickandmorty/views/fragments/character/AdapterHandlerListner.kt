package com.example.rickandmorty.views.fragments.character

interface AdapterHandlerListner {
    fun onLoadMore()
    fun getDataFromAdapter(obj: Any?)
}