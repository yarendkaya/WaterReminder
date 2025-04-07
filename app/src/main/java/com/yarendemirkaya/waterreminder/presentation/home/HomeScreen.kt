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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.app_color
import com.yarendemirkaya.waterreminder.R.color.background
import com.yarendemirkaya.waterreminder.R.color.dark_gray
import com.yarendemirkaya.waterreminder.R.color.light_background
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
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
    uiEffect.collectWithLifecycle {
        when (it) {
            is HomeContract.HomeUiEffect.NavigateToProfile -> {
                onNavigateToProfileScreen()
            }

            is HomeContract.HomeUiEffect.ShowToast -> {}
        }
    }

    if (uiState.showEditDialog) {
        EditProfileDialog(onAction = onAction)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = light_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp)
                .background(color = colorResource(id = light_background))
        ) {
            Text(text = stringResource(id = R.string.welcome_back), style =WaterTypography().heading1)
            Text(text = uiState.userName,style = WaterTypography().name1)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StreakCard(23)
            if (uiState.showSetReminderDialog) {
                SetReminderDialog(onDismiss = {
                    onAction(HomeContract.HomeUiAction.OnClickCloseSetReminderDialog)
                },
                    onConfirm = { onAction(HomeContract.HomeUiAction.OnCLickOpenSetReminderDialog) })
            }
//            Icon(
//                painter = painterResource(id = R.drawable.ic_alarm),
//                contentDescription = "Icon",
//                modifier = Modifier
//                    .padding(end = 8.dp)
//                    .align(Alignment.End)
//                    .clickable { onAction(HomeContract.HomeUiAction.OnCLickOpenSetReminderDialog) },
//                tint = Color.Unspecified
//            )

            WaterIntakeProgressBar(successPercentage =
                uiState.percentOfSuccess.toFloat())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = background),
                contentColor = Color.White
            ), onClick = {
                onAction(
                    HomeContract.HomeUiAction.OnClickAddWaterIntake(
                        WaterIntake(
                            time = System.currentTimeMillis().toString()
                        )
                    )
                )
            }) {
                Text(text = stringResource(id = R.string.add_water))
            }

            if (uiState.isAddWaterDialogOpen) {
                AddWaterDialog(onAction = onAction)
            }

            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = background),
                contentColor = Color.White
            ), onClick = {
                onAction(HomeContract.HomeUiAction.OnClickOpenDialog)
            }) {
                Text(text = stringResource(id = R.string.add_icon))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = stringResource(id = R.string.todays_water), style = WaterTypography().text1
        )
        WaterGrid(waterIntakes = uiState.waterIntakes, onDeleteClick = {
            onAction(HomeContract.HomeUiAction.OnClickDeleteWaterIntake(it))
        })
    }
}


@Composable
fun AddWaterDialog(onAction: (HomeContract.HomeUiAction) -> Unit) {
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onAction(HomeContract.HomeUiAction.OnClickCloseDialog)
        },
        title = {
            Text(
                text = "Add Water Intake",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(text = "Amount (ml)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            ElevatedButton(
                onClick = {
                    onAction(
                        HomeContract.HomeUiAction.OnClickAddWaterIntake(
                            WaterIntake(
                                amount = amount.toIntOrNull() ?: 0,
                                time = System.currentTimeMillis().toString()
                            )
                        )
                    )
                    onAction(HomeContract.HomeUiAction.OnClickCloseDialog)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = app_color),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Add", style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onAction(HomeContract.HomeUiAction.OnClickCloseDialog)
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(id = app_color)
                )
            ) {
                Text(text = "Close", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}


@Composable
fun WaterGrid(waterIntakes: List<WaterIntake>, onDeleteClick: (WaterIntake) -> Unit) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
            .background(
                color = colorResource(id= light_background),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = colorResource(id = background),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state = scrollState
        ) {
            items(waterIntakes) { waterIntake ->
                WaterItem(waterIntake = waterIntake, onDeleteClick = {
                    onDeleteClick(waterIntake)
                })
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