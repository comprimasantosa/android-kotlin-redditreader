package com.primasantosa.android.redditreader.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.primasantosa.android.redditreader.R
import com.primasantosa.android.redditreader.adapter.HomeFeedAdapter
import com.primasantosa.android.redditreader.databinding.ActivityMainBinding
import com.primasantosa.android.redditreader.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val homeFeedAdapter = HomeFeedAdapter()
        binding.mainFeedRv.adapter = homeFeedAdapter

        mainViewModel.feed.observe(this, Observer {
            homeFeedAdapter.submitList(it)
        })

        mainViewModel.isProgressBarShown.observe(this, Observer {
            if (it) {
                binding.mainFeedProgressbar.visibility = View.VISIBLE
            } else {
                binding.mainFeedProgressbar.visibility = View.GONE
            }
        })
    }
}
