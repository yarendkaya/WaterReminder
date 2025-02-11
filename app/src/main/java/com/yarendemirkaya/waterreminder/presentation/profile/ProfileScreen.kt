package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel,navController: NavController) {
    val userState by viewModel.userState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil") },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            userState?.let { user ->
                Column(
                    modifier = Modifier.run {
                        fillMaxWidth()
                            .padding(16.dp)
                    },
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileItem(label = "Ad", value = user.name)
                    ProfileItem(label = "Boy", value = "${user.height} cm")
                    ProfileItem(label = "Kilo", value = "${user.weight} kg")
                    ProfileItem(label = "Yaş", value = "${user.age}")
                    ProfileItem(label = "Cinsiyet", value = user.gender)
                    ProfileItem(label = "Günlük Su Hedefi", value = "${user.dailyWaterGoal} ml")
                    ProfileItem(label = "Uyku Saati", value = user.sleepTime)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate("editProfile")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Bilgileri Düzenle")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value, color = Color.Gray)
    }
}
