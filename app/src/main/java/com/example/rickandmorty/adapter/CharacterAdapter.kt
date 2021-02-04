package com.example.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding


/*
* Adapter for showing Character list
* */
class CharacterAdapter(private val mContext: Context) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    inner class MyViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }
}