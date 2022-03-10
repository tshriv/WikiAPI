package com.ts.wikipages

import com.ts.wikipages.models.model_category.CategoryListModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val baseUrlforCategoriesandRandom: String =
    "https://en.wikipedia.org/w/"

private const val baseUrlforFeatureImage: String =
    "https://commons.wikimedia.org/w/"

private val retrofitForCategories = Retrofit.Builder()
    .baseUrl(baseUrlforCategoriesandRandom)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val retrofitForFeatureImages = Retrofit.Builder()
    .baseUrl(baseUrlforFeatureImage)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

private val retrofitForRandomPages = Retrofit.Builder().baseUrl(baseUrlforCategoriesandRandom)
    .addConverterFactory(ScalarsConverterFactory.create()).build()

interface WikiCategoryListApiService {
    @GET("api.php?format=json&action=query&list=allcategories&aclimit=15&acprefix=List%20of&formatversion=2")
    suspend fun getCategoryList(): CategoryListModel

}

interface WikiRandomPageApiServices {
    @GET("api.php?action=query&generator=random&grnlimit=10&grnnamespace=0&prop=info&format=json&utf8")
    suspend fun getRandomPages(): String

    @GET("api.php?action=query&prop=extracts&exsentences=4&exlimit=1&explaintext=1&formatversion=2&format=json&utf8")
    suspend fun getRandomPageDescriptions(@Query("titles") title: String): String

    @GET("api.php?action=query&generator=random&grnlimit=10&grnnamespace=0&prop=info&format=json&utf8")
    suspend fun getRandomPageContinued(@Query("grncontinue") continuee: String): String

}


interface WikiFeaturedImageApiService {

    @GET("api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8")
    suspend fun getFeatureImages(): String

}

interface WikiFeaturedImageContinueApiService {

    @GET("api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8")
    suspend fun getFeatureImages(@Query("gcmcontinue") continueStr: String): String

}


object WikiCategoryListApi {
    val retrofitService: WikiCategoryListApiService by lazy {
        retrofitForCategories.create(
            WikiCategoryListApiService::class.java
        )
    }
}

object WikiFeatureImagesApi {
    val retrofitService: WikiFeaturedImageApiService by lazy {
        retrofitForFeatureImages.create(
            WikiFeaturedImageApiService::class.java
        )
    }
}

object WikiRandomPagesApi {
    val retrofitService: WikiRandomPageApiServices by lazy {
        retrofitForRandomPages.create(WikiRandomPageApiServices::class.java)
    }
}

object WikiFeatureImagesContinueApi {
    val retrofitService: WikiFeaturedImageContinueApiService by lazy {
        retrofitForFeatureImages.create(
            WikiFeaturedImageContinueApiService::class.java
        )
    }
}