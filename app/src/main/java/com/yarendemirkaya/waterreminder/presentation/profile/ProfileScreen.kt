package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yarendemirkaya.waterreminder.data.models.User

@Composable
fun ProfileSetupScreen(viewModel: ProfileViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dailyGoal by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Adınız") })
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Yaşınız") })
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Boyunuz (cm)") })
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Kilonuz (kg)") })
        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Cinsiyet") })
        OutlinedTextField(
            value = dailyGoal,
            onValueChange = { dailyGoal = it },
            label = { Text("Günlük Su Hedefiniz (ml)") })
        OutlinedTextField(
            value = sleepTime,
            onValueChange = { sleepTime = it },
            label = { Text("Uyku Saati (HH:MM)") })

        Button(onClick = {
            viewModel.saveUserData(
                User(
                    name = name,
                    age = age.toInt(),
                    height = height.toInt(),
                    weight = weight.toInt(),
                    gender = gender,
                    dailyWaterGoal = dailyGoal.toInt(),
                    sleepTime = sleepTime,
                    id = "",
                )
            )
        }) {
            Text("Kaydet")
        }
    }


}
