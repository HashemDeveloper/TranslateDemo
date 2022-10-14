package com.yikyaktranslate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yikyaktranslate.data.RemoteRepo
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.model.ResponseWrapper
import com.yikyaktranslate.presentation.viewmodel.TranslateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule


@OptIn(ExperimentalCoroutinesApi::class)
class TranslateViewModelShould {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val translateViewModel: TranslateViewModel
    private val remoteRepo: RemoteRepo = mock()
    private val expectedResponse = mock<ResponseWrapper<MutableList<Language>, Boolean, Exception>>()

    init {
        runTest {
            whenever(remoteRepo.getLanguages()).thenReturn(expectedResponse)
        }
        this.translateViewModel = TranslateViewModel(remoteRepo)
    }

    @Test
    fun getLanguagesFromRepo() = runTest {
        verify(remoteRepo, times(1)).getLanguages()
    }

    @Test
    fun test_getting_languageFromRepo() = runTest {
        assertEquals(expectedResponse, translateViewModel.languages.value)
    }
}