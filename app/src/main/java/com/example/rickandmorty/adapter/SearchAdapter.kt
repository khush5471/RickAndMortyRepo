package com.example.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemSearchBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.MulticlickHandler
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner


/*
* Adapter for showing Search Character list
* */
class SearchAdapter(private val mContext: Context, private val mListner: AdapterHandlerListner) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val mCharacterList = ArrayList<CharacterItem?>()
    private var mIsMoreLoading: Boolean = false
    private var mFirstVisibleItem: Int = 0
    private var mVisibleItemCount: Int = 0
    private var mTotalItemCount: Int = 0
    private var mIsLastPage: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mCharacterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder) {
            with(mCharacterList[position]) {
                binding.txtSearch.text = this?.name

            }
        }
    }


    /*
    * Setting recyclerview pagination*/
    fun setRecyclerView(linearLayout: LinearLayoutManager, recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    mVisibleItemCount = linearLayout.childCount
                    mTotalItemCount = linearLayout.itemCount
                    mFirstVisibleItem = linearLayout.findFirstVisibleItemPosition()
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
    * Clear adapter when we have no character entered in edittext.
    * */
    fun clearAdapter() {
        mCharacterList.clear()
        notifyDataSetChanged()
        mIsMoreLoading = false
        mIsLastPage = false
    }

    /*
    * If there is no internet connection then reset the mIsMoreLoading to false so that whenever
    * the user to bottom of the list  it will always shows for network connection error.
    * */
    fun resetLoadingFlag(changeState: Boolean) {
        mIsLastPage = false
        mIsMoreLoading = changeState
    }

    inner class MyViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                if (MulticlickHandler.singleClick())
                    mListner.getDataFromAdapter(mCharacterList[adapterPosition])
            }
        }
    }
}