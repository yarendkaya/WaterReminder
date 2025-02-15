package com.yarendemirkaya.waterreminder.presentation.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ProfileEditScreen(
    user: User,
    uiEffect: SharedFlow<EditProfileContract.EditProfileUiEffect>,
    onNavigateToProfileScreen: () -> Unit,
    onAction: (EditProfileContract.EditProfileUiAction) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf(user.name) }
    var height by rememberSaveable { mutableStateOf(user.height.toString()) }
    var weight by rememberSaveable { mutableStateOf(user.weight.toString()) }
    var age by rememberSaveable { mutableStateOf(user.age.toString()) }
    var gender by rememberSaveable { mutableStateOf(user.gender) }
    var dailyWaterGoal by rememberSaveable { mutableStateOf(user.dailyWaterGoal.toString()) }
    var sleepTime by rememberSaveable { mutableStateOf(user.sleepTime) }

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {
                EditProfileContract.EditProfileUiEffect.NavigateToProfile -> {
                    onNavigateToProfileScreen()
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = name,
                onValueChange = { name = it },
                label = { Text("Name") })
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Gender") })
            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") })
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") })
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height") })
            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text("Sleep Time") })
            OutlinedTextField(
                value = dailyWaterGoal,
                onValueChange = { dailyWaterGoal = it },
                label = { Text("Daily Water Goal") })

            Button(
                onClick = {
                    onAction(
                        EditProfileContract.EditProfileUiAction.OnClickSaveChanges(
                            user.copy(
                                name = name,
                                height = height.toIntOrNull() ?: user.height,
                                weight = weight.toIntOrNull() ?: user.weight,
                                age = age.toIntOrNull() ?: user.age,
                                gender = gender,
                                dailyWaterGoal = dailyWaterGoal.toIntOrNull()
                                    ?: user.dailyWaterGoal,
                                sleepTime = sleepTime
                            )
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}




