package com.yikyaktranslate.presentation.viewmodel

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.*
import com.yikyaktranslate.data.RemoteRepo
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.ResponseWrapper
import com.yikyaktranslate.model.TranslateObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// extending from ViewModel rather than AndroidViewModel to be able to test view model
// also having static context instance might lead to memory leak!
@HiltViewModel
class TranslateViewModel @Inject() constructor(private val remoteRepo: RemoteRepo): ViewModel() {

    // displays the translated text. Could use the response wrapper to wrap the different state such loading/error etc
    // and display whenever necessary
    private val _translatedText: MutableStateFlow<String> = MutableStateFlow("")
    val translatedText = _translatedText

    val languages: MutableState<ResponseWrapper<MutableList<Language>,Boolean,Exception>> = mutableStateOf(
        ResponseWrapper(null,true,null)
    )
    init {
        loadLanguages()
    }
    // Index within languages/languagesToDisplay that the user has selected
    val targetLanguageIndex = mutableStateOf(0)

    // Text that the user has input to be translated
    private val _textToTranslate = MutableStateFlow(TextFieldValue(""))
    val textToTranslate = _textToTranslate.asLiveData()

    /**
     * Loads the languages from our service
     */
    private fun loadLanguages() {
        viewModelScope.launch(Dispatchers.IO) {
            languages.value = remoteRepo.getLanguages()
        }
    }

    /**
     * Updates the data when there's new text from the user
     *
     * @param newText TextFieldValue that contains user input we want to keep track of
     */
    fun onInputTextChange(newText: TextFieldValue) {
        _textToTranslate.value = newText
    }

    /**
     * Updates the selected target language when the user selects a new language
     *
     * @param newLanguageIndex Represents the index for the chosen language in the list of languages
     */
    fun onTargetLanguageChange(newLanguageIndex: Int) {
        targetLanguageIndex.value = newLanguageIndex
    }

    fun translate(source: String) {
        val target: Language? = this.languages.value.data?.elementAtOrElse(this.targetLanguageIndex.value){} as Language?
        val value: String? = this.textToTranslate.value?.text
        viewModelScope.launch(Dispatchers.IO) {
            target?.code?.let { code ->
              val responseWrapper =  remoteRepo.translate(value ?: "",source, code)
                responseWrapper.data?.translatedText?.let {text ->
                    _translatedText.value = text
                }
            }
        }
    }
}