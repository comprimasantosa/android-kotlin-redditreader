package com.primasantosa.android.redditreader.model

import com.squareup.moshi.Json

data class Feed(
    @Json(name = "name") val key: String,
    @Json(name = "title") val title: String,
    @Json(name = "subreddit_name_prefixed") val subreddit: String,
    @Json(name = "author") val author: String,
    @Json(name = "created") val time: Long,
    @Json(name = "score") val score: Int,
    @Json(name = "num_comments") val numComments: Int,
    @Json(name = "thumbnail") val thumbnail: String
//    @Json(name = "permalink") val comment: List<FeedDetail>
)