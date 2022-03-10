package com.ts.wikipages.models.model_category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Allcategory(
    @SerializedName("category")
    @Expose
    var category: String? = null
)