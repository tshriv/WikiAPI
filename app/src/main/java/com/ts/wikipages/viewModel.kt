package com.ts.wikipages

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ts.wikipages.models.RandomPage
import com.ts.wikipages.models.RandomPageModel
import com.ts.wikipages.models.featured_image.FeaturedImageModel
import com.ts.wikipages.models.featured_image.Imageinfo
import com.ts.wikipages.models.featured_image.Page
import com.ts.wikipages.models.model_category.CategoryListModel
import kotlinx.coroutines.launch
import org.json.JSONObject

class viewModel : ViewModel() {

    var imageListSize: Int = 0
    var categoryList: CategoryListModel? = null
    var imageList: FeaturedImageModel? = FeaturedImageModel()
    var randomPages: RandomPageModel? = RandomPageModel()
    val isimageListchanged: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCategoryListChanged: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRandomPageListChanged: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            loadCategoryList()
            loadFeaturedImagesforFirstTime()
            loadRandomPages()
        }
    }

    private suspend fun loadRandomPages() {
        val jsonAsString: String = WikiRandomPagesApi.retrofitService.getRandomPages()
        parseRandomPages(jsonAsString)
        isRandomPageListChanged.value = true
    }

    suspend fun loadRandomPagesContinued() {
        val jsonAsString: String =
            WikiRandomPagesApi.retrofitService.getRandomPageContinued(randomPages?.continuee!!)
        parseRandomPages(jsonAsString)
    }

    suspend fun loadRandomPagedescription(title: String, position: Int) {
        val jsonAsString = WikiRandomPagesApi.retrofitService.getRandomPageDescriptions(title)
        val json = JSONObject(jsonAsString)
        randomPages?.pages?.get(position)?.content?.value =
            json.getJSONObject("query").getJSONArray("pages").getJSONObject(0).getString("extract")
    }

    private fun parseRandomPages(jsonAsString: String) {

        val jsonObj = JSONObject(jsonAsString)
        randomPages?.continuee = jsonObj.getJSONObject("continue").getString("grncontinue")
        val pages: JSONObject = jsonObj.getJSONObject("query").getJSONObject("pages")
        for (pageKey in pages.keys()) {
            val page = pages.getJSONObject(pageKey)
            randomPages?.pages?.add(
                RandomPage(
                    page.getString("pageid"),
                    page.getString("title"), MutableLiveData("null")
                )
            )
        }

    }


    private suspend fun loadCategoryList() {

        categoryList = WikiCategoryListApi.retrofitService.getCategoryList()
        isCategoryListChanged.value = true
        for (cat in categoryList!!.query?.allcategories!!)
            cat.category?.let { Log.d("getNow", it) }
    }

    suspend fun loadFeaturedImagesforFirstTime() {

        val jsonAsString = WikiFeatureImagesApi.retrofitService.getFeatureImages()
        Log.d("DirectJson", "${jsonAsString}")
//            imageList!!.let { Log.d("mainImageList", "$it") }

        parseImages(jsonAsString)
    }

    suspend fun continueLoadingImages() {
        val jsonAsString =
            imageList?.continuee?.gcmcontinue?.let {
                WikiFeatureImagesContinueApi.retrofitService.getFeatureImages(it)
            }
        parseImages(jsonAsString!!)
        // isContinuedImagesLoaded = true

    }

    fun parseImages(jsonAsString: String) {
        val jsonObj: JSONObject = JSONObject(jsonAsString)


        imageList?.batchcomplete = jsonObj.getString("batchcomplete")
        Log.d("imagelistbachcomplete", "${imageList?.batchcomplete}")
        imageList?.continuee?.gcmcontinue =
            jsonObj.getJSONObject("continue").getString("gcmcontinue")
        imageList?.continuee?.continuee =
            jsonObj.getJSONObject("continue").getString("continue")
        val pagesJsonObj = jsonObj.getJSONObject("query").getJSONObject("pages")
        // imageList?.query?.pages?.pageList = arrayListOf()
        for (pageKey in pagesJsonObj.keys()) {
            val page = pagesJsonObj.getJSONObject(pageKey)
            val imageinfo = page.getJSONArray("imageinfo")
            val pageObj = Page(
                page.getString("pageid").toInt(),
                page.getString("ns").toInt(),
                page.getString("title"),
                page.getString("imagerepository"),
                Imageinfo(url = imageinfo.getJSONObject(0).getString("url"))
            )

            imageList?.query?.pages?.pageList?.add(pageObj)
            isimageListchanged.value = true

        }
    }
}