package com.primasantosa.android.redditreader.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.primasantosa.android.redditreader.model.FeedResponse
import com.primasantosa.android.redditreader.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Build moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Build retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface RedditApiService {
    // Get home feed
    @GET(".json")
    fun getHomeFeed(
        @Query("before") before: String? = null,
        @Query("after") after: String? = null,
        @Query("limit") limit: Int = 30
    ): Deferred<FeedResponse>

//    // Get home feed detail and its comments
//    @GET("")
}

object RedditApi {
    // Create retrofit service
    val retrofitService: RedditApiService by lazy {
        retrofit.create(RedditApiService::class.java)
    }
}