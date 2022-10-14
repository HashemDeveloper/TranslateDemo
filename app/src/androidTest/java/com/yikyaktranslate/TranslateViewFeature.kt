package com.yikyaktranslate

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yikyaktranslate.app.MainActivity
import com.yikyaktranslate.presentation.view.DisplayDropDown
import com.yikyaktranslate.presentation.view.LanguageDropDown
import com.yikyaktranslate.utils.Constants

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TranslateViewFeature {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_main_container() {
        this.composeRule.onNodeWithTag(Constants.TRANSLATE_VIEW_MAIN_CONTAINER).assertExists()
    }

    @Test
    fun LanguageDropDown_textView_show_language() {
        val list = listOf("English","Arabic","Azerbaijani","Chinese")
        this.composeRule.activity.setContent {
            LanguageDropDown(
                languages = list,
                targetLanguageIndex = 2,
                onTargetLanguageSelected = {})
        }
       this.composeRule.onNode(hasAnyChild(hasTestTag(Constants.TRANSLATE_DROPDOWN_MENU_TEXT)))
           .assertIsDisplayed().onChildren().assertCountEquals(1)
    }
    @Test
    fun languageDropDown_empty_list_textView() {
        this.composeRule.activity.setContent {
            LanguageDropDown(
                languages = emptyList(),
                targetLanguageIndex = 0,
                onTargetLanguageSelected = {})
        }
        this.composeRule.onNode(hasAnyChild(hasTestTag(Constants.TRANSLATE_DROPDOWN_MENU_TEXT))).assertIsNotDisplayed()
    }

    @Test
    fun displayDropDown_emptyList_doesNotDisplay() {
        this.composeRule.activity.setContent {
            DisplayDropDown(
                languages = emptyList(),
                expandLanguageList = true,
                onClose = {},
                onTargetLanguageSelected = {}
            )
        }
        this.composeRule.onNode(hasAnyChild(hasTestTag(Constants.TRANSLATE_DROPDOWN_MENU_DROPDOWN))).assertIsNotDisplayed()
    }
    @Test
    fun displayDropDown_notEmpty_show() {
        val list = listOf("English","Arabic","Azerbaijani","Chinese")
        this.composeRule.activity.setContent {
            DisplayDropDown(
                languages = list,
                expandLanguageList = true,
                onClose = {},
                onTargetLanguageSelected = {}
            )
        }
        this.composeRule.onNode(hasAnyChild(hasTestTag(Constants.TRANSLATE_DROPDOWN_MENU_DROPDOWN_ITEM))).assertIsDisplayed()
            .onChildren().assertCountEquals(4)
    }
}