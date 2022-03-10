package com.ts.wikipages.models.featured_image


data class FeaturedImageModel(

    var batchcomplete: String? = null,
    var continuee: Continue? = Continue(),
    var query: Query? = Query()

)