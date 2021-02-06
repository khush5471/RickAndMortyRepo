package com.example.rickandmorty.views.fragments.location

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
import com.example.rickandmorty.adapter.LocationAdapter
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.models.ResultsLocationItem
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.BaseFragment
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener,
    AdapterHandlerListner {

    private var mLocationBinding: FragmentLocationBinding? = null
    private lateinit var mLocationAdapter: LocationAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private val mViewModel: LocationViewModel by viewModels()
    private var mCurrentPage = 1
    private var mIsLastPage = false
    private var mIsDataLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mLocationBinding = FragmentLocationBinding.inflate(inflater, container, false)
        val view = mLocationBinding?.root
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
                mLocationBinding?.swipeRefreshLocation?.isRefreshing = true
                mViewModel.getLoactionList(mCurrentPage)
            }
        } else {
            mLocationBinding?.swipeRefreshLocation?.isRefreshing = false
            context?.let {
                if (isVisible) {
                    showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    /*
   * Initializing views.
   * */
    fun init() {

        mLocationBinding?.swipeRefreshLocation?.setOnRefreshListener(this)

        context?.let {
            mLocationAdapter = LocationAdapter(it, this)
            mLinearLayoutManager = LinearLayoutManager(it)
        }

        mLocationBinding?.let {
            it.recyclerLocation.layoutManager = mLinearLayoutManager
            it.recyclerLocation.adapter = mLocationAdapter
            mLocationAdapter.setRecyclerView(mLinearLayoutManager, it.recyclerLocation)

        }

    }


    /*
   * Attaching observers  to observe data from the viewmodels
   * */
    fun attachObservers() {
        mViewModel.mLocationData.observe(viewLifecycleOwner, Observer {

            //if pull to refresh is loading then cancel its loader
            mLocationBinding?.swipeRefreshLocation?.isRefreshing = false

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
                        mLocationAdapter.setListForPullToRefresh(it, mIsLastPage)
                    } else {
                        //this code will execute for pagination when current page value is more than 1
                        mLocationAdapter.addItemsForPagination(it, mIsLastPage)
                    }
                }
            }
        })

        mViewModel.mApiError.observe(viewLifecycleOwner, Observer {
            it?.errorMessage?.let {
                handleErrorState(it)
            }
        })
    }

    /*
   * Shows the error from API and Networks*/
    fun handleErrorState(errorMessage: String) {
        //if pull to refresh is loading then cancel its loader
        mLocationBinding?.swipeRefreshLocation?.isRefreshing = false

        //Make this flag to false so that new data can be loaded using pagination or pull to refresh
        mIsDataLoading = false

        /*
        * as error is occured so we must decrement the page count by one,because it was incremented ,and we have
        * not got the result yet, so to start the same api again we must decerement the page variable
        */
        mCurrentPage--


        context?.let {
            showToast(it, errorMessage)


        }
    }

    override fun onLoadMore() {

        if (!mIsDataLoading && !mIsLastPage) {

            if (Utils.isNetworkAvailable(context)) {
                //on pagination increase the current page by 1, so that it will load next page data
                mCurrentPage++

                //Make this flag true so that it shows data is loading.
                mIsDataLoading = true
                mViewModel.getLoactionList(mCurrentPage)
            } else {
                mIsDataLoading = false
                mLocationAdapter.resetLoadingFlag(false)
                context?.let {
                    showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    override fun getDataFromAdapter(obj: Any?) {

        val charObj = obj as ResultsLocationItem

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //destroying view
        mLocationBinding = null
    }
}