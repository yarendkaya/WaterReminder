package com.yarendemirkaya.waterreminder.presentation.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.flow.Flow


@Composable
fun ProfileEditScreen(
    user: User,
    uiEffect: Flow<EditProfileContract.EditProfileUiEffect>,
    onNavigateToProfileScreen: () -> Unit,
    onAction: (EditProfileContract.EditProfileUiAction) -> Unit,
) {
    val scrollState = rememberScrollState()

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
            .background(color = colorResource(id = R.color.app_color).copy(alpha = 0.5f))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profil Fotoğrafı",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, color = Color.White, CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Profili Düzenle",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EditProfileItem(value = name, label = "Ad") { name = it }
            EditProfileItem(value = age, label = "Yaş", isNumber = true) { age = it }
            EditProfileItem(value = height, label = "Boy (cm)", isNumber = true) { height = it }
            EditProfileItem(value = weight, label = "Kilo (kg)", isNumber = true) { weight = it }
            EditProfileItem(value = gender, label = "Cinsiyet") { gender = it }
            EditProfileItem(value = goal, label = "Günlük Su Hedefi (ml)", isNumber = true) {
                goal = it
            }
            EditProfileItem(value = sleepTime, label = "Uyku Saati") { sleepTime = it }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
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
            Text(text = "Kaydet", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun EditProfileItem(
    value: String,
    label: String,
    isNumber: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = if (isNumber) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default
            )
        }
    }
}





