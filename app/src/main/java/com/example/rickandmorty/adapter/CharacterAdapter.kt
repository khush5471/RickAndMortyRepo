package com.example.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.character.OnLoadMoreListner


/*
* Adapter for showing Character list
* */
class CharacterAdapter(private val mContext: Context, private val mListner: OnLoadMoreListner) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private val mCharacterList = ArrayList<CharacterItem?>()
    private var mIsMoreLoading: Boolean = false
    private var mFirstVisibleItem: Int = 0
    private var mVisibleItemCount: Int = 0
    private var mTotalItemCount: Int = 0
    private var mIsLastPage: Boolean = false

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
    * Setting recyclerview pagination*/
    fun setRecyclerView(gridLayoutManager: GridLayoutManager, recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    mVisibleItemCount = gridLayoutManager.childCount
                    mTotalItemCount = gridLayoutManager.itemCount
                    mFirstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                    //If the recycler is not Loading and data is not of the end page then
                    //trigger the LoadMore event
                    if (!mIsMoreLoading && !mIsLastPage) {
                        if ((mFirstVisibleItem + mVisibleItemCount) >= mTotalItemCount
                            && mFirstVisibleItem >= 0
                            && mTotalItemCount >= Constants.PAGE_SIZE
                        ) {

                            mIsMoreLoading = true
                            mListner.onLoadMore()

                        }
                    }
                }
            }
        })

    }

    /*
   * For setting pull to refresh items in adapter*/
    fun setListForPullToRefresh(List: List<CharacterItem?>, isLastPage: Boolean) {

        mCharacterList.clear()
        mCharacterList.addAll(List)
        notifyDataSetChanged()
        mIsMoreLoading = false
        mIsLastPage = isLastPage

    }

    /*
    * For setting pagination items in adapter*/
    fun addItemsForPagination(list: List<CharacterItem?>, isLastPage: Boolean) {

        mCharacterList.addAll(list)
        notifyDataSetChanged()
        mIsMoreLoading = false
        mIsLastPage = isLastPage

    }

    /*
    * If there is no internet connection then reset the mIsMoreLoading to false so that whenever
    * the user to bottom of the list  it will always shows for network connection error.
    * */
    fun resetLoadingFlag(changeState: Boolean) {
        mIsLastPage = false
        mIsMoreLoading = changeState
    }

    inner class MyViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }
}