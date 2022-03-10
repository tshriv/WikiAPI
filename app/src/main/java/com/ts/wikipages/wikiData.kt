package com.ts.wikipages

import com.squareup.moshi.Json

data class wikiData(val pageID: String, @Json(name = "url") val img_info: String)