package com.yarendemirkaya.waterreminder.presentation.register

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
import com.yarendemirkaya.waterreminder.presentation.login.LoginContract

@Composable
fun RegisterScreen(
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    onNavigateToLoginScreen: (LoginContract.LoginUiEffect.GoToLoginScreen) -> Unit,
    onNavigateToHomeScreen: (LoginContract.LoginUiEffect.GoToHomeScreen) -> Unit,
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Register")
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.email,
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
            onAction(LoginContract.LoginUiAction.SignUpClicked)
            onNavigateToHomeScreen(LoginContract.LoginUiEffect.GoToHomeScreen)
        }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { onNavigateToLoginScreen(LoginContract.LoginUiEffect.GoToLoginScreen) }
        ) {
            Text(text = "Already have an account? Login")
        }
    }
}