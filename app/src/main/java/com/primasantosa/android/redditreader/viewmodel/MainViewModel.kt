package com.primasantosa.android.redditreader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.primasantosa.android.redditreader.model.Feed
import com.primasantosa.android.redditreader.repository.FeedDataSource
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {
    // Hold PagedList data
    val feed: LiveData<PagedList<Feed>>

    // Track ProgressBar state
    val isProgressBarShown = FeedDataSource.isLoadingNow

    init {
        feed = buildPagedListBuilder(provideDataSourceFactory(), setPagedListConfig()).build()
    }

    // Builds a LiveData<PagedList>, based on DataSource.Factory and a PagedList.Config.
    private fun buildPagedListBuilder(
        dataSourceFactory: DataSource.Factory<String, Feed>,
        pagedListConfig: PagedList.Config
    ): LivePagedListBuilder<String, Feed> {
        return LivePagedListBuilder<String, Feed>(dataSourceFactory, pagedListConfig)
    }

    // Provide DataSource.Factory
    private fun provideDataSourceFactory(): DataSource.Factory<String, Feed> {
        return object : DataSource.Factory<String, Feed>() {
            override fun create(): DataSource<String, Feed> {
                return FeedDataSource(Dispatchers.IO)
            }
        }
    }

    // Provide PagedList.Config
    private fun setPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(30)
            .setEnablePlaceholders(false)
            .build()
    }
}