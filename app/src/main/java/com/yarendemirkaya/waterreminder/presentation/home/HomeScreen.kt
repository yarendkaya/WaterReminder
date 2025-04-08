package com.yarendemirkaya.waterreminder.presentation.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.background
import com.yarendemirkaya.waterreminder.R.color.light_background
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import com.yarendemirkaya.waterreminder.presentation.home.components.AddWaterDialog
import com.yarendemirkaya.waterreminder.presentation.home.components.EditProfileDialog
import com.yarendemirkaya.waterreminder.presentation.home.components.SetReminderDialog
import com.yarendemirkaya.waterreminder.presentation.home.components.StreakCard
import com.yarendemirkaya.waterreminder.presentation.home.components.WaterIntakeProgressBar
import com.yarendemirkaya.waterreminder.presentation.home.components.WaterItem
import com.yarendemirkaya.waterreminder.ui.theme.WaterTypography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow


@Composable
fun HomeScreen(
    uiState: HomeContract.HomeUiState,
    uiEffect: Flow<HomeContract.HomeUiEffect>,
    onAction: (HomeContract.HomeUiAction) -> Unit,
    onNavigateToProfileScreen: () -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is HomeContract.HomeUiEffect.NavigateToProfile -> onNavigateToProfileScreen()
            is HomeContract.HomeUiEffect.ShowToast -> Unit
        }
    }


    if (uiState.showEditDialog) {
        EditProfileDialog(onAction = onAction)
    }

    if (uiState.showSetReminderDialog) {
        SetReminderDialog(
            onDismiss = {
                onAction(HomeContract.HomeUiAction.OnClickCloseSetReminderDialog)
            },
            onConfirm = {
                onAction(HomeContract.HomeUiAction.OnCLickOpenSetReminderDialog)
            }
        )
    }

    if (uiState.isAddWaterDialogOpen) {
        AddWaterDialog(onAction = onAction)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = light_background))
    ) {
        GreetingSection(userName = uiState.userName)
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StreakCard(streakCount = 23)
            Column(modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.Start)
                .border(1.dp, color = colorResource(id = background), shape = RoundedCornerShape(8.dp))) {
                Text(text = stringResource(id = R.string.target), style = WaterTypography().text2)
                Text(text = "${uiState.dailyGoal} ml", style = WaterTypography().text2)
            }
            WaterIntakeProgressBar(successPercentage = uiState.percentOfSuccess.toFloat())
        }

        Spacer(modifier = Modifier.height(8.dp))
        ActionButtons(onAction = onAction)

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.todays_water),
            style = WaterTypography().text1,
            modifier = Modifier.padding(start = 16.dp)
        )

        WaterGrid(
            waterIntakes = uiState.waterIntakes,
            onDeleteClick = {
                onAction(HomeContract.HomeUiAction.OnClickDeleteWaterIntake(it))
            }
        )
    }
}

@Composable
fun GreetingSection(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, start = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = WaterTypography().heading1
        )
        Text(
            text = userName,
            style = WaterTypography().name1
        )
    }
}

@Composable
fun ActionButtons(onAction: (HomeContract.HomeUiAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                onAction(
                    HomeContract.HomeUiAction.OnClickAddWaterIntake(
                        WaterIntake(time = System.currentTimeMillis().toString())
                    )
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = background),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(id = R.string.add_water), style = WaterTypography().btnText)
        }

        Button(
            onClick = { onAction(HomeContract.HomeUiAction.OnClickOpenDialog) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = background),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(id = R.string.add_icon), style = WaterTypography().btnText)
        }
    }
}

@Composable
fun WaterGrid(
    waterIntakes: List<WaterIntake>,
    onDeleteClick: (WaterIntake) -> Unit
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
            .background(
                color = colorResource(id = light_background),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = colorResource(id = background),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(waterIntakes) { waterIntake ->
                WaterItem(
                    waterIntake = waterIntake,
                    onDeleteClick = { onDeleteClick(waterIntake) }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeContract.HomeUiState(),
        uiEffect = MutableSharedFlow(),
        onAction = {},
        onNavigateToProfileScreen = {})
}