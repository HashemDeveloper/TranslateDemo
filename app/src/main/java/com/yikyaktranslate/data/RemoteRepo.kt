package com.yikyaktranslate.data

import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.ResponseWrapper
import com.yikyaktranslate.model.TranslateObj
import com.yikyaktranslate.service.face.TranslateAPI
import javax.inject.Inject

class RemoteRepo @Inject constructor(private val translateAPI: TranslateAPI) {
    private val langListResWrapper: ResponseWrapper<MutableList<Language>,Boolean,Exception> = ResponseWrapper()
    private val translateResWrapper: ResponseWrapper<TranslateObj,Boolean,Exception> = ResponseWrapper()

    /**
     * Fetches all the languages from the server
     */
    suspend fun getLanguages(): ResponseWrapper<MutableList<Language>,Boolean,Exception> {
        try {
            this.langListResWrapper.isLoading = true
            val response = this.translateAPI.getLanguages()
            if (response.isNotEmpty()) {
                this.langListResWrapper.isLoading = false
                this.langListResWrapper.data = response
            }
        } catch (error: Exception) {
            this.langListResWrapper.isLoading = false
            this.langListResWrapper.error = error
        }
        return this.langListResWrapper
    }
    /**@param value text to translate
     * @param source source of the language such as translating from English to Spanish, English is the source.
     * @param target targeted language the text will be translated to
     */
    suspend fun translate(value: String, source: String, target: String): ResponseWrapper<TranslateObj,Boolean,Exception> {
        try {
            this.translateResWrapper.isLoading = true
            val response: TranslateObj = this.translateAPI.translate(value, source, target)
            val isError: Boolean? = with(response) {
                this.error?.isNotEmpty()
            }
            isError?.let { error ->
                this.translateResWrapper.isLoading = false
                if (error) {
                    this.translateResWrapper.error = Exception(response.error)
                }
                return@let
            }
            this.translateResWrapper.data = response
        } catch (error: Exception) {
            this.translateResWrapper.isLoading = false
            this.translateResWrapper.error = error
        }
        return this.translateResWrapper
    }
}