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
import com.yarendemirkaya.waterreminder.presentation.editprofile.EditProfileViewModel
import com.yarendemirkaya.waterreminder.presentation.editprofile.ProfileEditScreen
import com.yarendemirkaya.waterreminder.presentation.home.HomeScreen
import com.yarendemirkaya.waterreminder.presentation.home.HomeViewModel
import com.yarendemirkaya.waterreminder.presentation.intro.IntroScreen
import com.yarendemirkaya.waterreminder.presentation.intro.IntroViewModel
import com.yarendemirkaya.waterreminder.presentation.login.LoginContract
import com.yarendemirkaya.waterreminder.presentation.login.LoginScreen
import com.yarendemirkaya.waterreminder.presentation.login.LoginViewModel
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileContract
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileScreen
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileViewModel
import com.yarendemirkaya.waterreminder.presentation.register.RegisterScreen
import com.yarendemirkaya.waterreminder.presentation.register.RegisterViewModel
import com.yarendemirkaya.waterreminder.presentation.splash.SplashScreen
import com.yarendemirkaya.waterreminder.presentation.splash.SplashViewModel


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
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


            LaunchedEffect(uiEffect, lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getWaterIntakes()
                    viewModel.checkUserHasData()
                }
            }

            HomeScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
                onNavigateToEditProfileScreen = {
                    navController.navigate("editProfile")
                }
            )
        }

        composable("profile") {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(uiEffect, lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiEffect.collect { effect ->
                        when (effect) {
                            is ProfileContract.ProfileUiEffect.NavigateToEdit -> {
                                navController.navigate("editProfile")
                            }
                        }
                    }
                }
            }
            ProfileScreen(
                uiState = uiState,
                onNavigateToEditProfileScreen = {
                    navController.navigate("editProfile")
                },
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
            )
        }

        composable(route = "editProfile") {

            val viewModel: EditProfileViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect

            ProfileEditScreen(
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToProfileScreen = {
                    navController.navigate("profile")
                }
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
                onAction = viewModel::onAction,
                uiEffect = uiEffect
            )
        }
        composable("intro") {
            val viewModel: IntroViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect

            IntroScreen(
                onAction = viewModel::onAction,
                onNavigateToLoginScreen = {
                    navController.navigate("login")
                },
                onNavigateToRegisterScreen = {
                    navController.navigate("register")
                },
                uiEffect = uiEffect,
                onNavigateToHomeScreen = {
                    navController.navigate("home")
                }
            )
        }

        composable("splash") {

            val viewModel: SplashViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect

            LaunchedEffect(uiEffect) {
                viewModel.checkIsUserLoggedIn()
            }

            SplashScreen(
                uiEffect = uiEffect,
                onNavigateToIntroScreen = {
                    navController.navigate("intro")
                },
                onNavigateToHomeScreen = {
                    navController.navigate("home")
                }
            )
        }
    }
}