package com.spotifyplayer.ui

import  android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spotifyplayer.R
import com.spotifyplayer.adapter.SearchVerticalAdapter
import com.spotifyplayer.databinding.ActivityListWithoutPagingBinding
import com.spotifyplayer.enums.Status
import kotlinx.android.synthetic.main.activity_list_without_paging.*


class ListWithoutPaging : BaseActivity() {

    var viewModel: ListWithoutPagingViewModel? = null

    var binding: ActivityListWithoutPagingBinding? = null

    companion object {
        fun intentFor(context: Context, isTestMode: Boolean): Intent {
            val intent = Intent(context, ListWithoutPaging::class.java)
            intent.putExtra(isTestModeKey, isTestMode)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_list_without_paging)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(
            this,
            ListWithoutPagingViewModel.Factory(
                tokenRequest = restRequest,
                isTestMode = isTestMode,
                executor = appExecuter
            )
        ).get(ListWithoutPagingViewModel::class.java)
        subscribeToModel(viewModel = viewModel!!)
        updateUiCallBack()
        updateListbySearchText()
    }

    fun subscribeToModel(viewModel: ListWithoutPagingViewModel) {
        val adapter = SearchVerticalAdapter()
        viewModel.data.observe(this, Observer {
            adapter.submitItem(it)
        })
        viewModel.networkState.observe(this, Observer {
            viewModel.showProgress(it)
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.setNetworkState(it)
                }
                Status.RUNNING -> {
                    hideKeyboard()
                }
                else -> {
                    binding!!.buttonRetry.text = it.msg
                }
            }
        })
        binding!!.buttonRetry.setOnClickListener {
            viewModel.retry()
        }
        recyclerView.adapter = adapter
        binding!!.viewModel = viewModel
    }

    fun updateUiCallBack() {
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListbySearchText()
                true
            } else {
                false
            }
        }
        inputEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListbySearchText()
                true
            } else {
                false
            }
        }
    }

    fun updateListbySearchText() {
        inputEditText.text!!
            .trim().toString()
            .let {
                viewModel!!.query.postValue(it)
            }
    }
}
