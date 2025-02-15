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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun ProfileScreen(
    uiState: ProfileContract.ProfileUiState,
    onNavigateToEditProfileScreen: (User) -> Unit,
    uiEffect: SharedFlow<ProfileContract.ProfileUiEffect>,
    onAction: (ProfileContract.ProfileUiAction) -> Unit,
) {

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is ProfileContract.ProfileUiEffect.NavigateToEdit -> {
                onNavigateToEditProfileScreen(uiState.user)
            }
        }
    }

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Profil", modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            uiState.let { uiState ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileItem(label = "Ad", value = uiState.user.name)
                    ProfileItem(label = "Boy", value = "${uiState.user.height} cm")
                    ProfileItem(label = "Kilo", value = "${uiState.user.weight} kg")
                    ProfileItem(label = "Yaş", value = uiState.user.age.toString())
                    ProfileItem(label = "Cinsiyet", value = uiState.user.gender)
                    ProfileItem(
                        label = "Günlük Su Hedefi",
                        value = "${uiState.user.dailyWaterGoal} ml"
                    )
                    ProfileItem(label = "Uyku Saati", value = uiState.user.sleepTime)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onAction(ProfileContract.ProfileUiAction.OnClickEdit(uiState.user))
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
