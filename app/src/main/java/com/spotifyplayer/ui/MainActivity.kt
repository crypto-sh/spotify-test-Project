package com.spotifyplayer.ui


import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.spotifyplayer.R
import com.spotifyplayer.adapter.SearchPagedAdapter
import com.spotifyplayer.databinding.ActivityMainBinding
import com.spotifyplayer.models.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    var binding : ActivityMainBinding?      = null
    var viewModel : MainActivityViewModel?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

//        viewModel = ViewModelProviders
//            .of(this, MainActivityViewModel.Factory(
//                database =
//                tokenRequest = getRestRequest(),
//                executer = appExecuter)
//
//            ).get(MainActivityViewModel::class.java)

        subscribeToModel(viewModel!!)
        updateUiCallBack()
        updateListbySearchText()
    }

    fun subscribeToModel(viewModel: MainActivityViewModel){
        val adapter = SearchPagedAdapter()
        recyclerView.adapter = adapter
        viewModel.networkState.observe(this, Observer {
            when(it.status){
                 Status.SUCCESS -> {
                    binding!!.showingProgress = false
                    adapter.setNetworkState(it)
                }
                Status.RUNNING -> {
                    binding!!.showingProgress = true
                }
                else -> {
                    binding!!.messageTextView.text = it.msg
                    binding!!.showingProgress = false
                }
            }
        })
        viewModel.data.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    fun updateUiCallBack(){
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListbySearchText()
                true
            } else {
                false
            }
        }
        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListbySearchText()
                true
            } else {
                false
            }
        }
    }

    fun updateListbySearchText(){
        searchEditText.text!!
            .trim().toString()
            .let {
            viewModel!!.searchResultShow(it)
        }
    }
}
