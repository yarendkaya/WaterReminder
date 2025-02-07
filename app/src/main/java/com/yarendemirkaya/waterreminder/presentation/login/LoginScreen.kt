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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R

@Composable
fun LoginScreen(
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    onNavigateToRegisterScreen: (LoginContract.LoginUiEffect.GoToRegisterScreen) -> Unit,
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.application_name))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.description))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.login_title))
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.email,
            onValueChange = { onAction(LoginContract.LoginUiAction.EmailChanged(it)) },
            label = { Text(stringResource(id = R.string.login_email_hint)) })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.password,
            onValueChange = { onAction(LoginContract.LoginUiAction.PasswordChanged(it)) },
            label = { Text(stringResource(id = R.string.login_password_hint)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onAction(LoginContract.LoginUiAction.SignInClicked)
        }) {
            Text(text = stringResource(id = R.string.login_button))
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            onNavigateToRegisterScreen(LoginContract.LoginUiEffect.GoToRegisterScreen)
        }) {
            Text(text = stringResource(id = R.string.register_button))
        }

        TextButton(onClick = {
            onNavigateToRegisterScreen(LoginContract.LoginUiEffect.GoToRegisterScreen)
        }) {
            Text(text = "Don't have an account? Register")
        }
    }
}