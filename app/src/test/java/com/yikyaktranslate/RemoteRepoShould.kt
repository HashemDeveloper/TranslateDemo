package com.yikyaktranslate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yikyaktranslate.data.RemoteRepo
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.service.face.TranslateAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteRepoShould {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remoteRep: RemoteRepo
    private val translateAPI: TranslateAPI = mock()
    private val languageList = mock<MutableList<Language>>()

    init {
        remoteRep = RemoteRepo(this.translateAPI)
    }

    @Test
    fun getLanguagesFromService() = runTest {
        remoteRep.getLanguages()
        verify(translateAPI, times(1)).getLanguages()
    }

    @Test
    fun dispatch_playFromService() = runTest {
        whenever(translateAPI.getLanguages()).thenReturn(languageList)
        assertEquals(languageList, remoteRep.getLanguages().data)
    }
}