package com.yikyaktranslate.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.yikyaktranslate.R
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.TranslateObj
import com.yikyaktranslate.presentation.view.TranslateView
import com.yikyaktranslate.presentation.viewmodel.TranslateViewModel

@Composable
fun TranslateScreen(viewModel: TranslateViewModel) {
    val inputText by viewModel.textToTranslate.observeAsState(
        TextFieldValue("")
    )
    val languages: MutableList<Language>? = viewModel.languages.value.data
    val targetLanguageIndex by viewModel.targetLanguageIndex
    val sourceLngCode: String = stringResource(id = R.string.source_language_code)

    val translatedText = viewModel.translatedText.collectAsState().value
    TranslateView(
        inputText = inputText,
        onInputChange = viewModel::onInputTextChange,
        languages = languages?.map { it.name },
        targetLanguageIndex = targetLanguageIndex,
        onTargetLanguageSelected = viewModel::onTargetLanguageChange,
        onTranslateClick = {
                           viewModel.translate(sourceLngCode)
        },
        translatedText = translatedText
    )

}