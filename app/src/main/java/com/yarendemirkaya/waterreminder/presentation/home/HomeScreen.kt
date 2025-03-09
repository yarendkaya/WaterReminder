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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.app_color
import com.yarendemirkaya.waterreminder.R.color.dark_gray
import com.yarendemirkaya.waterreminder.R.color.light_background
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import com.yarendemirkaya.waterreminder.presentation.home.components.EditProfileDialog
import com.yarendemirkaya.waterreminder.presentation.home.components.LottieAnimation
import com.yarendemirkaya.waterreminder.presentation.home.components.WaterItem
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
            .padding(16.dp)
            .background(color = colorResource(id = light_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp)
                .background(color = colorResource(id = light_background))
        ) {
            Text(text = stringResource(id = R.string.welcome_back), fontSize = 36.sp)
            Text(text = uiState.userName, fontSize = 24.sp, color = colorResource(id = dark_gray))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.End),
                tint = Color.Unspecified)
            LottieAnimation()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = app_color),
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

            if (uiState.isDialogOpen) {
                AddWaterDialog(onAction = onAction)
            }

            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = app_color),
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
            text = stringResource(id = R.string.todays_water), fontSize = 24.sp,
            color = colorResource(id = dark_gray)
        )
        WaterGrid(waterIntakes = uiState.waterIntakes, onDeleteClick = {
            onAction(HomeContract.HomeUiAction.OnClickDeleteWaterIntake(it))
        })
    }
}


@Composable
fun AddWaterDialog(onAction: (HomeContract.HomeUiAction) -> Unit) {
    Column {
        var amount by remember { mutableStateOf("") }
        TextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = { Text(text = "Amount") }
        )
        Row {
            Button(onClick = {
                onAction(
                    HomeContract.HomeUiAction.OnClickAddWaterIntake(
                        WaterIntake(
                            amount = amount.toIntOrNull() ?: 0,
                            time = System.currentTimeMillis().toString()
                        )
                    )
                )
            }) {
                Text(text = "Add")
            }
            Button(onClick = {
                onAction(HomeContract.HomeUiAction.OnClickCloseDialog)
            }) {
                Text(text = "Close")
            }
        }
    }
}

@Composable
fun WaterGrid(waterIntakes: List<WaterIntake>, onDeleteClick: (WaterIntake) -> Unit) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = colorResource(id = app_color),
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