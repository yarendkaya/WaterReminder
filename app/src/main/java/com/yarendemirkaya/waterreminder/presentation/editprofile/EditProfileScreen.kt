package com.yarendemirkaya.waterreminder.presentation.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {
                EditProfileContract.EditProfileUiEffect.NavigateToProfile -> {
                    onNavigateToProfileScreen()
                }
            }
        }
    }


    var name by rememberSaveable { mutableStateOf(user.name) }
    var age by rememberSaveable { mutableStateOf(user.age.toString()) }
    var height by rememberSaveable { mutableStateOf(user.height.toString()) }
    var weight by rememberSaveable { mutableStateOf(user.weight.toString()) }
    var gender by rememberSaveable { mutableStateOf(user.gender) }
    var goal by rememberSaveable { mutableStateOf(user.dailyWaterGoal.toString()) }
    var sleepTime by rememberSaveable { mutableStateOf(user.sleepTime) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Edit Profile")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") })
        OutlinedTextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Daily Water Goal (ml)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = sleepTime,
            onValueChange = { sleepTime = it },
            label = { Text("Sleep Time") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updatedUser = user.copy(
                name = name,
                age = age.toIntOrNull() ?: user.age,
                height = height.toIntOrNull() ?: user.height,
                weight = weight.toIntOrNull() ?: user.weight,
                gender = gender,
                dailyWaterGoal = goal.toIntOrNull() ?: user.dailyWaterGoal,
                sleepTime = sleepTime
            )
            onAction(EditProfileContract.EditProfileUiAction.OnClickSaveChanges(updatedUser))
        }) {
            Text(text = "Save")
        }
    }
}




