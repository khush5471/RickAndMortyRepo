package com.example.rickandmorty.views.fragments.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharacterAdapter
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.Utils
import com.example.rickandmorty.views.activities.holder.HolderActivity
import com.example.rickandmorty.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener,
    AdapterHandlerListner {

    private var mCharacterBinding: FragmentCharacterBinding? = null
    private lateinit var mCharacterAdapter: CharacterAdapter
    private lateinit var mGridLayoutManager: GridLayoutManager
    private val mViewModel: CharacterViewModel by viewModels()
    private var mCurrentPage = 1
    private var mIsLastPage = false
    private var mIsDataLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCharacterBinding = FragmentCharacterBinding.inflate(inflater, container, false)
        val view = mCharacterBinding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachObservers()
        onRefresh()
        mCharacterBinding?.edtCharName?.setOnClickListener {
//            val charObj = obj as CharacterItem
            val bundle = Bundle()
//            bundle.putParcelable(Constants.PARCEL_DATA, charObj)
            bundle.putInt(Constants.PARCEL_KEY, Constants.SEARCH)
            startActivityFromFragment(activity, HolderActivity::class.java, bundle, false)

        }
    }

    override fun onRefresh() {
        if (Utils.isNetworkAvailable(context)) {
            if (!mIsDataLoading) {

                //Make this flag true so that it shows data is loading.
                mIsDataLoading = true
                mCurrentPage = 1
                mCharacterBinding?.swipeRefresh?.isRefreshing = true
                mViewModel.getCharacterListData(mCurrentPage)
            }
        } else {
            mCharacterBinding?.swipeRefresh?.isRefreshing = false
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

        mCharacterBinding?.swipeRefresh?.setOnRefreshListener(this)

        context?.let {
            mCharacterAdapter = CharacterAdapter(it, this)
            mGridLayoutManager = GridLayoutManager(it, 2, GridLayoutManager.VERTICAL, false)
        }

        mCharacterBinding?.let {
            it.recyclerCharacters.layoutManager = mGridLayoutManager
            it.recyclerCharacters.adapter = mCharacterAdapter
            mCharacterAdapter.setRecyclerView(mGridLayoutManager, it.recyclerCharacters)

        }

    }


    /*
    * Attaching observers  to observe data from the viewmodels
    * */
    fun attachObservers() {
        mViewModel.mCharacterData.observe(viewLifecycleOwner, Observer {

            //if pull to refresh is loading then cancel its loader
            mCharacterBinding?.swipeRefresh?.isRefreshing = false

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
                        mCharacterAdapter.setListForPullToRefresh(it, mIsLastPage)
                    } else {
                        //this code will execute for pagination when current page value is more than 1
                        mCharacterAdapter.addItemsForPagination(it, mIsLastPage)
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
        mCharacterBinding?.swipeRefresh?.isRefreshing = false

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


    /*
    * Will load new data by starting pagination*/
    override fun onLoadMore() {

        if (!mIsDataLoading && !mIsLastPage) {

            if (Utils.isNetworkAvailable(context)) {
                //on pagination increase the current page by 1, so that it will load next page data
                mCurrentPage++

                //Make this flag true so that it shows data is loading.
                mIsDataLoading = true
                mViewModel.getCharacterListData(mCurrentPage)
            } else {
                mIsDataLoading = false
                mCharacterAdapter.resetLoadingFlag(false)
                context?.let {
                    showToast(it, getString(R.string.no_internet_message))
                }
            }
        }
    }

    override fun getDataFromAdapter(obj: Any?) {

        val charObj = obj as CharacterItem
        val bundle = Bundle()
        bundle.putParcelable(Constants.PARCEL_DATA, charObj)
        bundle.putInt(Constants.PARCEL_KEY, Constants.CHARCTER_DETAIL)
        startActivityFromFragment(activity, HolderActivity::class.java, bundle, false)

    }


    override fun onDestroy() {
        super.onDestroy()
        mCharacterBinding = null
    }


}