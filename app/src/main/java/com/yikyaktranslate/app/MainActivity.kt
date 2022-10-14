package com.yikyaktranslate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.yikyaktranslate.R
import com.yikyaktranslate.presentation.navigation.AppNavigation
import com.yikyaktranslate.presentation.theme.YikYakTranslateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntryPoint()
        }
    }

    @Composable
    fun AppEntryPoint() {
        YikYakTranslateTheme {
            AppNavigation()
        }
    }
}

