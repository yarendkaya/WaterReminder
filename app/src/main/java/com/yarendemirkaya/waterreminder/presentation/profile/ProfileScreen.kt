package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.light_background
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ProfileScreen(
    uiState: ProfileContract.ProfileUiState,
    onNavigateToEditProfileScreen: (User) -> Unit,
    uiEffect: Flow<ProfileContract.ProfileUiEffect>,
    onAction: (ProfileContract.ProfileUiAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is ProfileContract.ProfileUiEffect.NavigateToEdit -> {
                onNavigateToEditProfileScreen(effect.user)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = light_background))
    ) {
        WavyBackground(modifier =Modifier.fillMaxWidth())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profil Fotoğrafı",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(3.dp, color = Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.user.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileItem(label = "Boy", value = "${uiState.user.height} cm")
                ProfileItem(label = "Kilo", value = "${uiState.user.weight} kg")
                ProfileItem(label = "Yaş", value = uiState.user.age.toString())
                ProfileItem(label = "Cinsiyet", value = uiState.user.gender)
                ProfileItem(label = "Günlük Su Hedefi", value = "${uiState.user.dailyWaterGoal} ml")
                ProfileItem(label = "Uyku Saati", value = uiState.user.sleepTime)
            }

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    onAction(ProfileContract.ProfileUiAction.OnClickEdit(uiState.user))
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(id = R.color.dark_gray),
                    contentColor = Color.White
                )

            ) {
                Text("Bilgileri Düzenle", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = value, color = Color.Gray, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val fakeUser = User(
        name = "Mehmet Yılmaz",
        height = 175,
        weight = 70,
        age = 25,
        gender = "Erkek",
        dailyWaterGoal = 2500,
        sleepTime = "23:00"
    )

    val uiState = ProfileContract.ProfileUiState(user = fakeUser)

    ProfileScreen(
        uiState = uiState,
        onNavigateToEditProfileScreen = {},
        uiEffect = emptyFlow(),
        onAction = {}
    )
}

@Composable
fun WavyBackground(modifier: Modifier) {
    val appColor = colorResource(id = R.color.app_color)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height * 0.2f)
            cubicTo(
                width * 0.25f, height * 0.4f,
                width * 0.75f, height * 0.1f,
                width, height * 0.2f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = path,
            color = appColor.copy(alpha = 0.5f),
        )
    }
}