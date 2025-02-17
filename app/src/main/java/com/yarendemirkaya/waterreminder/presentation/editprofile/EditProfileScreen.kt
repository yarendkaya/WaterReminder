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
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var goal by rememberSaveable { mutableStateOf("") }
    var sleepTime by rememberSaveable { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = goal,
                onValueChange = { goal = it },
                label = { Text("Goal") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                val user = User(
                    name = name,
                    age = age.toInt(),
                    weight = weight.toInt(),
                    height = height.toInt(),
                    gender = gender,
                    dailyWaterGoal = goal.toInt(),
                    sleepTime = sleepTime
                )
                onAction(EditProfileContract.EditProfileUiAction.OnClickSaveChanges(user))
            }) {
                Text(text = "Update")
            }
        }
    }
}




