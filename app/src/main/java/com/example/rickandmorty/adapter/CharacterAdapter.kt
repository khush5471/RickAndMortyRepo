package com.example.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Utils


/*
* Adapter for showing Character list
* */
class CharacterAdapter(private val mContext: Context) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private val mCharacterList = ArrayList<CharacterItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mCharacterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder) {
            with(mCharacterList[position]) {
                binding.txtCharacterName.text = this?.name
                binding.txtStatus.text = this?.status
                binding.txtCurrentSpecies.text = this?.species
                binding.txtCurrentGender.text = this?.gender
                this?.image?.let {
                    Utils.downloadImageByGlide(mContext, it, binding.imgCharacter)
                }

            }
        }
    }

    /*
   * For setting pull to refresh items in adapter*/
    fun setListForPullToRefresh(List: List<CharacterItem?>) {

        mCharacterList.clear()
        mCharacterList.addAll(List)
        notifyDataSetChanged()
//        mIsMoreLoading = false
//        mIsLastPage = false


    }

    inner class MyViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }
}