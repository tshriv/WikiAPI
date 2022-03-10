package com.ts.wikipages.models.featured_image

data class Page(
    var pageid: Int? = null,
    var ns: Int? = null,
    var title: String? = null,
    var imagerepository: String? = null,
    var imageinfo: Imageinfo? = null
) {
}