package com.example.rickandmorty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemEpisodeBinding
import com.example.rickandmorty.models.ResultsEpisodeItem
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner


/*
* Adapter for showing Character list
* */
class EpisodeAdapter(private val mContext: Context, private val mListner: AdapterHandlerListner) :
    RecyclerView.Adapter<EpisodeAdapter.MyViewHolder>() {

    private val mEpisodeList = ArrayList<ResultsEpisodeItem?>()
    private var mIsMoreLoading: Boolean = false
    private var mFirstVisibleItem: Int = 0
    private var mVisibleItemCount: Int = 0
    private var mTotalItemCount: Int = 0
    private var mIsLastPage: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mEpisodeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder) {
            with(mEpisodeList[position]) {
                binding.txtEpisodeName.text = this?.name
                binding.txtEpisodeDate.text = this?.airDate
                binding.txtEpisodeNumber.text = this?.episode
            }
        }
    }


    /*
    * Setting recyclerview pagination*/
    fun setRecyclerView(linearLayoutManager: LinearLayoutManager, recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    mVisibleItemCount = linearLayoutManager.childCount
                    mTotalItemCount = linearLayoutManager.itemCount
                    mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
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
    fun setListForPullToRefresh(List: List<ResultsEpisodeItem?>, isLastPage: Boolean) {

        mEpisodeList.clear()
        mEpisodeList.addAll(List)
        notifyDataSetChanged()
        mIsMoreLoading = false
        mIsLastPage = isLastPage

    }

    /*
    * For setting pagination items in adapter*/
    fun addItemsForPagination(list: List<ResultsEpisodeItem?>, isLastPage: Boolean) {

        mEpisodeList.addAll(list)
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

    inner class MyViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }
}