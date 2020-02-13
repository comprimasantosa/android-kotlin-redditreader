package com.primasantosa.android.redditreader.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.primasantosa.android.redditreader.model.Feed
import com.primasantosa.android.redditreader.network.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class FeedDataSource(coroutineContext: CoroutineContext) : PageKeyedDataSource<String, Feed>() {

    companion object {
        private val isLoading = MutableLiveData<Boolean>()
        val isLoadingNow: LiveData<Boolean>
            get() = isLoading
    }

    // Declare coroutine job + scope
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Feed>
    ) {
        scope.launch {
            isLoading.postValue(true)
            val response =
                RedditApi.retrofitService.getHomeFeed(
                    limit = params.requestedLoadSize
                )
            try {
                val listing = response.await().data
                val feed = listing.children.map { it.data }
                callback.onResult(feed, listing.before, listing.after)
                isLoading.postValue(false)
            } catch (e: Exception) {
                Timber.e("$e")
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Feed>) {
        scope.launch {
            val response =
                RedditApi.retrofitService.getHomeFeed(
                    limit = params.requestedLoadSize,
                    after = params.key
                )
            try {
                val listing = response.await().data
                val feed = listing.children.map { it.data }
                callback.onResult(feed, listing.after)
            } catch (e: Exception) {
                Timber.e("$e")
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Feed>) {
        scope.launch {
            val response =
                RedditApi.retrofitService.getHomeFeed(
                    limit = params.requestedLoadSize,
                    before = params.key
                )
            try {
                val listing = response.await().data
                val feed = listing.children.map { it.data }
                callback.onResult(feed, listing.before)
            } catch (e: Exception) {
                Timber.e("$e")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}