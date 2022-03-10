package com.ts.wikipages.models.model_category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryListModel(

    @SerializedName("batchcomplete")
    @Expose
    var batchcomplete: Boolean? = null,

    @SerializedName("continue")
    @Expose
    var _continue: Continue? = null,

    @SerializedName("query")
    @Expose
    var query: Query? = null
)