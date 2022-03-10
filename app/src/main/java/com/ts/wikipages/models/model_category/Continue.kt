package com.ts.wikipages.models.model_category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Continue(
    @SerializedName("accontinue")
    @Expose
    var accontinue: String? = null,

    @SerializedName("continue")
    @Expose
    var _continue: String? = null
)