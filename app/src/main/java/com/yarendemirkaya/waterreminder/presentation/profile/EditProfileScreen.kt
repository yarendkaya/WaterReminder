package com.yarendemirkaya.waterreminder.presentation.profile

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ProfileEditScreen(
    viewModel: ProfileViewModel,
    uiState: ProfileContract.ProfileUiState,
    uiEffect: SharedFlow<ProfileContract.ProfileUiEffect>,
    onAction: (ProfileContract.ProfileUiAction) -> Unit, // Kaydedildikten sonra geri dönmek için callback
) {

    var name by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dailyWaterGoal by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }



//    LaunchedEffect(userState) {
//        userState?.let { user ->
//            name = user.name
//            height = user.height.toString()
//            weight = user.weight.toString()
//            age = user.age.toString()
//            gender = user.gender
//            dailyWaterGoal = user.dailyWaterGoal.toString()
//            sleepTime = user.sleepTime
//        }
//    }

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Ad") })
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Boy (cm)") })
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Kilo (kg)") })
            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Yaş") })
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Cinsiyet") })
            OutlinedTextField(
                value = dailyWaterGoal,
                onValueChange = { dailyWaterGoal = it },
                label = { Text("Günlük Su Hedefi (ml)") })
            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text("Uyku Saati (HH:mm)") })


            Button(
                onClick = {
                    viewModel.updateUser(
                        name = name,
                        height = height.toInt(),
                        weight = weight.toInt(),
                        age = age.toInt(),
                        gender = gender,
                        dailyWaterGoal = dailyWaterGoal.toInt(),
                        sleepTime = sleepTime,
                    )
                    onAction(
                        ProfileContract.ProfileUiAction.UpdateUser(
                            name = name,
                            height = height.toInt(),
                            weight = weight.toInt(),
                            age = age.toInt(),
                            gender = gender,
                            dailyWaterGoal = dailyWaterGoal.toInt(),
                            sleepTime = sleepTime,
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kaydet")
            }
        }
    }
}

