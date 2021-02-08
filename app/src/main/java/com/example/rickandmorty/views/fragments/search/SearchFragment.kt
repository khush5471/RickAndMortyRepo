package com.example.rickandmorty.views.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.SearchAdapter
import com.example.rickandmorty.databinding.FragmentSearchBinding
import com.example.rickandmorty.di.RetrofitBuilderModule
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharacterListResponse
import com.example.rickandmorty.network.NetworkResult
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.fragments.BaseFragment
import com.example.rickandmorty.views.fragments.character.AdapterHandlerListner
import com.example.rickandmorty.views.fragments.character.CharacterDetailFragment
import dagger.hilt.android.AndroidEntryPoint

/*
* This fragments manages searching characters on basis of name and renders Character list UI through API.*/
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
        setListnerOnViews()
        attachObservers()
    }

    /*
   * Initializing views and setting recycler adapter
   * */
    private fun init() {


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
    * Setting listners on views and manages edittext textwatcher to handle search character .
    * */
    private fun setListnerOnViews() {


        mBindingSearch?.edtCharName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                Log.e("TEXT ", "ok " + s.toString())
                val searchKey = s.toString().trim()
                mCurrentPage = 1

                if (searchKey.isNotEmpty()) {
                    context?.let {
                        if (Utils.isNetworkAvailable(it)) {
                            mViewModel.getSearchedData(mCurrentPage, searchKey)
                        } else {
                            showToast(it, getString(R.string.no_internet_message))
                        }
                    }

                } else {

                    //cancel search request on clear of edittext.
                    mViewModel.cancelSearchJob()
                    mSearchAdapter.clearAdapter()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        /*
        * Handles the hide of keyboard on scroll of recyclerview list.
        * */
        mBindingSearch?.recyclerSearch?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                hideKeyBoard(v)
                return false
            }

        })

        mBindingSearch?.imgBack?.setOnClickListener {
            activity?.onBackPressed()
        }
    }


    /*
    * Attaching observers  to observe data from the viewmodels
    * */
    private fun attachObservers() {
        mViewModel.mSearchList.observe(viewLifecycleOwner, Observer {

            when (it) {

                is NetworkResult.Success -> {
                    it.data.let {
                        HandleSuccesApiData(it)
                    }
                }

                is NetworkResult.Failure -> {
                    it.apply {
                        context?.let {
                            val error = RetrofitBuilderModule.handleApiError(this)
                            handleErrorState(error)
                        }
                    }
                }
            }

        })


    }


    /*
    * Handles the success data from the api and loads the episode list from api
    * into the recyclerview adapter.
     * */
    private fun HandleSuccesApiData(data: CharacterListResponse?) {
        //Make this flag to false so that new data can be loaded using pagination or pull to refresh
        mIsDataLoading = false

        data?.info?.next.let {
            //if the check is true it means there are no more pages to load using pagination
            if (it == null || it.isNullOrEmpty()) {
                mIsLastPage = true
                Log.e("LAST PAGE", "FOR PAGINATION")
            } else {
                mIsLastPage = false
            }
        }

        data?.let {
            it.results?.let {
                if (mCurrentPage == 1) {

                    /*
                    * On first time loading of data if there is any empty list then make no data available text visible else hide.
                    * */
                    if (it.isEmpty()) {
                        mBindingSearch?.txtError?.visibility = View.VISIBLE
                    } else {
                        mBindingSearch?.txtError?.visibility = View.GONE
                    }


                    //this code will execute for pull to refresh functionality
                    mSearchAdapter.setListForPullToRefresh(it, mIsLastPage)
                } else {
                    //this code will execute for pagination when current page value is more than 1
                    mSearchAdapter.addItemsForPagination(it, mIsLastPage)
                }
            }
        }
    }

    /*
  * Shows the error from API and Networks*/
    private fun handleErrorState(errorMessage: String) {
        mSearchAdapter.clearAdapter()


        //Make this flag to false so that new data can be loaded using pagination or pull to refresh
        mIsDataLoading = false

        /*
       * as error is occured so we must decrement the page count by one,because it was incremented ,and we have
       * not got the result yet, so to start the same api again we must decerement the page variable
       */
        mCurrentPage--

        mSearchAdapter.resetLoadingFlag(false)


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

    /*
    * Recives the click item from the adapter view and then redirect it to their respective fragment.
    * */
    override fun getDataFromAdapter(obj: Any?) {
        hideKeyBoard(mBindingSearch?.root)
        val character = obj as CharacterItem
        val bundle = Bundle()
        bundle.putParcelable(Constants.PARCEL_DATA, character)
        val fragment = CharacterDetailFragment()
        fragment.arguments = bundle
        attachFragment(fragment, true)
    }
}