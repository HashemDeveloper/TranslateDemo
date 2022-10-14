package com.yikyaktranslate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yikyaktranslate.presentation.screens.TranslateScreen
import com.yikyaktranslate.presentation.viewmodel.TranslateViewModel

@Composable
fun AppNavigation() {
    // Not Good! Hard to test
    val navController = rememberNavController()
    val viewModel: TranslateViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Routes.TRANSLATE_SCREEN.name) {
        composable(Routes.TRANSLATE_SCREEN.name) {
            TranslateScreen(viewModel)
        }
    }
}