package com.yarendemirkaya.waterreminder.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.yarendemirkaya.waterreminder.data.models.User
import com.yarendemirkaya.waterreminder.presentation.editprofile.EditProfileViewModel
import com.yarendemirkaya.waterreminder.presentation.editprofile.ProfileEditScreen
import com.yarendemirkaya.waterreminder.presentation.home.HomeScreen
import com.yarendemirkaya.waterreminder.presentation.home.HomeViewModel
import com.yarendemirkaya.waterreminder.presentation.intro.IntroScreen
import com.yarendemirkaya.waterreminder.presentation.intro.IntroViewModel
import com.yarendemirkaya.waterreminder.presentation.login.LoginContract
import com.yarendemirkaya.waterreminder.presentation.login.LoginScreen
import com.yarendemirkaya.waterreminder.presentation.login.LoginViewModel
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileScreen
import com.yarendemirkaya.waterreminder.presentation.profile.ProfileViewModel
import com.yarendemirkaya.waterreminder.presentation.register.RegisterScreen
import com.yarendemirkaya.waterreminder.presentation.register.RegisterViewModel
import com.yarendemirkaya.waterreminder.presentation.splash.SplashScreen
import com.yarendemirkaya.waterreminder.presentation.splash.SplashViewModel
import com.yarendemirkaya.waterreminder.presentation.statistics.StatisticsViewPager
import com.yarendemirkaya.waterreminder.presentation.statistics.monthly.MonthlyStatisticsViewModel
import com.yarendemirkaya.waterreminder.presentation.statistics.weekly.WeeklyStatisticsViewModel


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
                    uiState.waterIntake.time?.let { viewModel.getTodayIntakeByTime() }
                    viewModel.checkUserHasData()
                    viewModel.getUserName()
                }
            }

            HomeScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
                onNavigateToProfileScreen = {
                    navController.navigate("profile")
                }
            )
        }

        composable("profile") {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(uiEffect, lifecycleOwner) {
                viewModel.getUserData()
            }

            ProfileScreen(
                onNavigateToEditProfileScreen = { user ->
                    val userJson = Uri.encode(Gson().toJson(user))
                    navController.navigate("editProfile/$userJson")
                },
                onAction = viewModel::onAction,
                uiEffect = uiEffect,
                uiState = uiState
            )
        }

        composable(
            route = "editProfile/{user}",
            arguments = listOf(navArgument("user") { type = NavType.StringType })
        ) { backStackEntry ->

            val userJson = backStackEntry.arguments?.getString("user")
            val user = Gson().fromJson(userJson, User::class.java)


            val viewModel: EditProfileViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ProfileEditScreen(
                uiEffect = uiEffect,
                user = user,
                onAction = viewModel::onAction,
                onNavigateToProfileScreen = {
                    navController.navigate("profile")
                },
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

        composable("statistics") {
            val viewModel: WeeklyStatisticsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val monthlyViewModel: MonthlyStatisticsViewModel = hiltViewModel()
            val monthlyUiState by monthlyViewModel.uiState.collectAsStateWithLifecycle()


            StatisticsViewPager(
                weeklyStatisticsUiState = uiState,
                monthlyStatisticsUiState = monthlyUiState,
                onAction = viewModel::onAction,
                onActionMonthly = monthlyViewModel::onAction
            )

            LaunchedEffect(Unit) {
                viewModel.getWeeklyIntakeByTime()
                monthlyViewModel.getMonthlyIntakeByTime()
            }
        }
    }
}