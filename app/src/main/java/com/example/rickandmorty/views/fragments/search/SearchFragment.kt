package com.example.rickandmorty.views.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.SearchAdapter
import com.example.rickandmorty.databinding.FragmentSearchBinding
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.BaseFragment
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(), AdapterHandlerListner {

    private var mBindingSearch: FragmentSearchBinding? = null
    private lateinit var mSearchAdapter: SearchAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private val mViewModel: SearchViewModel by viewModels()
    private var mCurrentPage = 1
    private var mIsLastPage = false
    private var mIsDataLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindingSearch = FragmentSearchBinding.inflate(inflater, container, false)
        return mBindingSearch?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachObservers()
        setEditextListner()
    }

    /*
   * Initializing views.
   * */
    fun init() {


        context?.let {
            mSearchAdapter = SearchAdapter(it, this)
            mLinearLayoutManager = LinearLayoutManager(it)
        }

        mBindingSearch?.let {

            it.recyclerSearch.layoutManager = mLinearLayoutManager
            it.recyclerSearch.adapter = mSearchAdapter
            mSearchAdapter.setRecyclerView(mLinearLayoutManager, it.recyclerSearch)

        }

    }

    /*
    * Attaching observers  to observe data from the viewmodels
    * */
    fun attachObservers() {
        mViewModel.mSearchList.observe(viewLifecycleOwner, Observer {


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
                        mSearchAdapter.setListForPullToRefresh(it, mIsLastPage)
                    } else {
                        //this code will execute for pagination when current page value is more than 1
                        mSearchAdapter.addItemsForPagination(it, mIsLastPage)
                    }
                }
            }
        })

        mViewModel.mApiError.observe(viewLifecycleOwner, Observer {


            //Make this flag to false so that new data can be loaded using pagination or pull to refresh
            mIsDataLoading = false

            /*
           * as error is occured so we must decrement the page count by one,because it was incremented ,and we have
           * not got the result yet, so to start the same api again we must decerement the page variable
           */
            mCurrentPage--

            it?.apply {
                context?.let {
                    showToast(it, this.errorMessage)
                }
            }

        })
    }

    fun setEditextListner() {
        mBindingSearch?.edtCharName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.e("TEXT ", "ok " + s.toString())
                val searchKey = s.toString().trim()
                mCurrentPage = 1
                if (searchKey.isNotEmpty()) {
                    mViewModel.getSearchedData(mCurrentPage, searchKey)
                } else {
                    mViewModel.cancelRequest()
                    mSearchAdapter.clearAdapter()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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
                val searchKey = mBindingSearch?.edtCharName?.text.toString().trim()
                mViewModel.getSearchedData(mCurrentPage, searchKey)
            } else {
                mIsDataLoading = false
                mSearchAdapter.resetLoadingFlag(false)
                context?.let {
                    showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    override fun getDataFromAdapter(obj: Any?) {
    }
}