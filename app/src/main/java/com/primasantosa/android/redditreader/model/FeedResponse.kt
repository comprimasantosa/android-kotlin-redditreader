package com.primasantosa.android.redditreader.model

import com.squareup.moshi.Json

data class FeedResponse(
    @Json(name = "data") val data: FeedResponseChildren
)

data class FeedResponseChildren(
    @Json(name = "children") val children: List<FeedResponseData>,
    @Json(name = "after") val after: String?,
    @Json(name = "before") val before: String?
)

data class FeedResponseData(
    @Json(name = "data") val data: Feed
)