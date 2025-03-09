package com.yarendemirkaya.waterreminder.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun SplashScreen(
    uiEffect: Flow<SplashContract.UiEffect>,
    onNavigateToIntroScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
) {

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            SplashContract.UiEffect.NavigateToIntro -> onNavigateToIntroScreen()
            SplashContract.UiEffect.NavigateToHome -> onNavigateToHomeScreen()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Water Reminder", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}