package com.yarendemirkaya.waterreminder.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yarendemirkaya.waterreminder.presentation.home.HomeScreen
import com.yarendemirkaya.waterreminder.presentation.home.HomeViewModel
import com.yarendemirkaya.waterreminder.presentation.login.LoginContract
import com.yarendemirkaya.waterreminder.presentation.login.LoginScreen
import com.yarendemirkaya.waterreminder.presentation.login.LoginViewModel
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileEditScreen
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileScreen
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileViewModel
import com.yarendemirkaya.waterreminder.presentation.register.RegisterScreen
import com.yarendemirkaya.waterreminder.presentation.register.RegisterViewModel


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

            LaunchedEffect(Unit) {
                uiEffect.collect { effect ->
                    when (effect) {
                        is LoginContract.LoginUiEffect.ShowToast -> {
                            Log.d("LoginViewModel", "ShowToast: ${effect.message}")
                        }

                        is LoginContract.LoginUiEffect.GoToHomeScreen -> {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }

                        is LoginContract.LoginUiEffect.GoToRegisterScreen -> {
                            navController.navigate("register")
                        }
                    }
                }
            }
            LoginScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
                onNavigateToRegisterScreen = {
                    navController.navigate("register")
                }
            )
        }

        composable("home") {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current
            HomeScreen(
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }

        composable("profile") {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current
            ProfileScreen(
                uiState = uiState,
                onNavigateToEditProfileScreen = {
                    navController.navigate("editProfile")
                }
            )
        }

        composable("editProfile") {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current
            ProfileEditScreen(
                viewModel = viewModel,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }

        composable("register") {
            val viewModel: RegisterViewModel = hiltViewModel()
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