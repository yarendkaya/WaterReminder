package com.yarendemirkaya.waterreminder.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yarendemirkaya.waterreminder.presentation.home.HomeScreen
import com.yarendemirkaya.waterreminder.presentation.login.LoginContract
import com.yarendemirkaya.waterreminder.presentation.login.LoginScreen
import com.yarendemirkaya.waterreminder.presentation.login.LoginViewModel
import com.yarendemirkaya.waterreminder.presentation.register.RegisterScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(uiEffect, lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiEffect.collect {
                        when (it) {
                            is LoginContract.LoginUiEffect.ShowToast -> {
                                Log.d("LoginViewModel", "ShowToast: ${it.message}")
                            }

                            is LoginContract.LoginUiEffect.GoToHomeScreen -> {
                                navController.navigate("home")
                            }

                            else -> {}
                        }
                    }
                }
            }

            LoginScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
                onNavigateHomeScreen = {
                    navController.navigate("home")
                },
                onNavigateToRegisterScreen = {
                    navController.navigate("register")
                }
            )
        }

        composable("home") {
            HomeScreen()
        }

        composable("register") {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(uiEffect, lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiEffect.collect { }
                }
            }

            RegisterScreen(
                onNavigateToHomeScreen = {
                    navController.navigate("home")
                },
                onNavigateToLoginScreen = {
                    navController.navigate("login")
                },
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }
    }
}