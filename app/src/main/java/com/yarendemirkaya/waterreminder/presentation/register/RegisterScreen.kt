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
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun RegisterScreen(
    uiState: RegisterContract.RegisterUiState,
    onAction: (RegisterContract.RegisterUiAction) -> Unit,
    onNavigateToLoginScreen: (RegisterContract.RegisterUiEffect.GoToLoginScreen) -> Unit,
    onNavigateToHomeScreen: (RegisterContract.RegisterUiEffect.GoToHomeScreen) -> Unit,
    uiEffect: SharedFlow<RegisterContract.RegisterUiEffect>
) {


    uiEffect.collectWithLifecycle {
        when (it) {
            is RegisterContract.RegisterUiEffect.GoToHomeScreen -> {
                onNavigateToHomeScreen(it)
            }

            is RegisterContract.RegisterUiEffect.GoToLoginScreen -> {
                onNavigateToLoginScreen(it)
            }

            is RegisterContract.RegisterUiEffect.ShowToast -> {
                //showToast(it.message)
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Register")
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.email,
            onValueChange = { onAction(RegisterContract.RegisterUiAction.EmailChanged(it)) },
            label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.password,
            onValueChange = { onAction(RegisterContract.RegisterUiAction.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAction(RegisterContract.RegisterUiAction.SignUpClicked)
        }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { onAction(RegisterContract.RegisterUiAction.SignInClicked) }
        ) {
            Text(text = "Already have an account? Login")
        }
    }
}