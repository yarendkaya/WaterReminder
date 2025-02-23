package com.yarendemirkaya.waterreminder.presentation.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun IntroScreen(
    onAction: (IntroContract.IntroAction) -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToRegisterScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    uiEffect: SharedFlow<IntroContract.IntroUiEffect>
) {

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is IntroContract.IntroUiEffect.NavigateToLoginScreen -> {
                onNavigateToLoginScreen()
            }

            is IntroContract.IntroUiEffect.NavigateToRegisterScreen -> {
                onNavigateToRegisterScreen()
            }
            is IntroContract.IntroUiEffect.NavigateToHomeScreen -> {
                onNavigateToHomeScreen()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Water Reminder",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Stay hydrated and healthy!",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    onAction(IntroContract.IntroAction.LoginClicked)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Login", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    onAction(IntroContract.IntroAction.RegisterClicked)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text(text = "Register", color = Color.White)
            }
        }
    }
}