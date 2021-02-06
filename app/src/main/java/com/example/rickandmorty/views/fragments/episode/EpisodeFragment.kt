package com.example.rickandmorty.views.fragments.episode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.EpisodeAdapter
import com.example.rickandmorty.databinding.FragmentEpisodeBinding
import com.example.rickandmorty.models.ResultsEpisodeItem
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.BaseFragment
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener,
    AdapterHandlerListner {

    private var mEpisodeBinding: FragmentEpisodeBinding? = null
    private lateinit var mEpisodeAdapter: EpisodeAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private val mViewModel: EpisodeViewModel by viewModels()
    private var mCurrentPage = 1
    private var mIsLastPage = false
    private var mIsDataLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mEpisodeBinding = FragmentEpisodeBinding.inflate(inflater, container, false)
        val view = mEpisodeBinding?.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachObservers()
        onRefresh()
    }


    override fun onRefresh() {
        if (Utils.isNetworkAvailable(context)) {
            if (!mIsDataLoading) {

                //Make this flag true so that it shows data is loading.
                mIsDataLoading = true
                mCurrentPage = 1
                mEpisodeBinding?.swipeRefreshEpisode?.isRefreshing = true
                mViewModel.getEpisodeList(mCurrentPage)
            }
        } else {
            mEpisodeBinding?.swipeRefreshEpisode?.isRefreshing = false
            context?.let {
                if (isVisible) {
                    Utils.showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    /*
 * Initializing views.
 * */
    fun init() {

        mEpisodeBinding?.swipeRefreshEpisode?.setOnRefreshListener(this)

        context?.let {
            mEpisodeAdapter = EpisodeAdapter(it, this)
            mLinearLayoutManager = LinearLayoutManager(it)
        }

        mEpisodeBinding?.let {
            it.recyclerEpisode.layoutManager = mLinearLayoutManager
            it.recyclerEpisode.adapter = mEpisodeAdapter
            mEpisodeAdapter.setRecyclerView(mLinearLayoutManager, it.recyclerEpisode)

        }

    }

    /*
  * Attaching observers  to observe data from the viewmodels
  * */
    fun attachObservers() {
        mViewModel.mEpisodeData.observe(viewLifecycleOwner, Observer {

            //if pull to refresh is loading then cancel its loader
            mEpisodeBinding?.swipeRefreshEpisode?.isRefreshing = false

            //Make this flag to false so that new data can be loaded using pagination or pull to refresh
            mIsDataLoading = false

            it.info?.next.let {
                //if the check is true it means there are no more pages to load using pagination
                if (it == null || it.isNullOrEmpty()) {
                    mIsLastPage = true
                    Log.e("LAST PAGE", "FOR PAGINATION")
                } else {
                    mIsLastPage = false
                }
            }

            it?.let {
                it.results?.let {
                    if (mCurrentPage == 1) {
                        //this code will execute for pull to refresh functionality
                        mEpisodeAdapter.setListForPullToRefresh(it, mIsLastPage)
                    } else {
                        //this code will execute for pagination when current page value is more than 1
                        mEpisodeAdapter.addItemsForPagination(it, mIsLastPage)
                    }
                }
            }
        })

        mViewModel.mApiError.observe(viewLifecycleOwner, Observer {
            //if pull to refresh is loading then cancel its loader
            mEpisodeBinding?.swipeRefreshEpisode?.isRefreshing = false

            //Make this flag to false so that new data can be loaded using pagination or pull to refresh
            mIsDataLoading = false

            /*
           * as error is occured so we must decrement the page count by one,because it was incremented ,and we have
           * not got the result yet, so to start the same api again we must decerement the page variable
           */
            mCurrentPage--

            it?.apply {
                context?.let {
                    Utils.showToast(it, this.errorMessage)

                }
            }
        })
    }

    override fun onLoadMore() {
        if (!mIsDataLoading && !mIsLastPage) {

            if (Utils.isNetworkAvailable(context)) {
                //on pagination increase the current page by 1, so that it will load next page data
                mCurrentPage++

                //Make this flag true so that it shows data is loading.
                mIsDataLoading = true
                mViewModel.getEpisodeList(mCurrentPage)
            } else {
                mIsDataLoading = false
                mEpisodeAdapter.resetLoadingFlag(false)
                context?.let {
                    Utils.showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    override fun getDataFromAdapter(obj: Any?) {
        val charObj = obj as ResultsEpisodeItem

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Destroying viewbinding object
        mEpisodeBinding = null
    }
}