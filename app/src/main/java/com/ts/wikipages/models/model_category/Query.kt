package com.ts.wikipages.models.model_category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("allcategories")
    @Expose
    var allcategories: List<Allcategory>? = null
)