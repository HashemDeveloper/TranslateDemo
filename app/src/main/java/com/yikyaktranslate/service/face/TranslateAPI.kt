package com.yikyaktranslate.service.face

import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.TranslateObj
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface TranslateAPI {
    @GET("/languages")
    suspend fun getLanguages(): MutableList<Language>
    @POST("/translate")
    suspend fun translate(@Query("q") value: String, @Query("source") source: String, @Query("target") target: String): TranslateObj
}