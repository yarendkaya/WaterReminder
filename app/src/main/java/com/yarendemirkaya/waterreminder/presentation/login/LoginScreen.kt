package com.yarendemirkaya.waterreminder.presentation.login

import androidx.compose.foundation.background
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.app_color

@Composable
fun LoginScreen(
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    onNavigateToRegisterScreen: (LoginContract.LoginUiEffect.GoToRegisterScreen) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = app_color))
        ) {
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .align(Alignment.Center)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(text = stringResource(id = R.string.application_name), fontSize = 36.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.description))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.login_title), fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                value = uiState.email,
                onValueChange = { onAction(LoginContract.LoginUiAction.EmailChanged(it)) },
                label = { Text(stringResource(id = R.string.login_email_hint)) })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                value = uiState.password,
                onValueChange = { onAction(LoginContract.LoginUiAction.PasswordChanged(it)) },
                label = { Text(stringResource(id = R.string.login_password_hint)) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = app_color),
                contentColor = Color.White
            ), onClick = {
                onAction(LoginContract.LoginUiAction.SignInClicked)
            }) {
                Text(text = stringResource(id = R.string.login_button))
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                onNavigateToRegisterScreen(LoginContract.LoginUiEffect.GoToRegisterScreen)
            }) {
                Text(
                    text = "Don't have an account? Register",
                    color = colorResource(id = app_color)
                )
            }
        }
    }
}