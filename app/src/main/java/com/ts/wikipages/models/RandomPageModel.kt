package com.ts.wikipages.models

import androidx.lifecycle.MutableLiveData

data class RandomPageModel(
    var continuee: String? = null,
    var pages: MutableList<RandomPage> = mutableListOf()
)

data class RandomPage(
    var pageid: String? = null,
    var title: String? = null,
    val content: MutableLiveData<String?> = MutableLiveData("null")
)