package com.yarendemirkaya.waterreminder.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    onNavigateHomeScreen: (LoginContract.LoginUiEffect.GoToHomeScreen) -> Unit,
    onNavigateToRegisterScreen: (LoginContract.LoginUiEffect.GoToRegisterScreen) -> Unit,
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = uiState.email,
            onValueChange = { onAction(LoginContract.LoginUiAction.EmailChanged(it)) },
            label = { Text("Email") })

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.password,
            onValueChange = { onAction(LoginContract.LoginUiAction.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAction(LoginContract.LoginUiAction.SignInClicked)
        }) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            onNavigateToRegisterScreen(LoginContract.LoginUiEffect.GoToRegisterScreen)
        }) {
            Text(text = "Don't have an account? Register")
        }
    }
}