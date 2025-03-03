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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.window.Dialog
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.app_color
import com.yarendemirkaya.waterreminder.common.collectWithLifecycle
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.flow.SharedFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeContract.HomeUiState,
    uiEffect: SharedFlow<HomeContract.HomeUiEffect>,
    onAction: (HomeContract.HomeUiAction) -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {

    uiEffect.collectWithLifecycle {
        when (it) {
            is HomeContract.HomeUiEffect.NavigateToEditProfile -> {
                onNavigateToEditProfileScreen()
            }

            is HomeContract.HomeUiEffect.ShowToast -> {}
        }
    }


    if (uiState.showEditDialog) {
        Dialog(onDismissRequest = {  }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Hoş Geldiniz!Lütfen daha iyi bir deneyim için profil bilgilerinizi güncelleyin.", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        onAction(HomeContract.HomeUiAction.OnClickEditProfile)
                    }) {
                        Text("Profili Düzenle")
                    }
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = app_color))
        ) {
            Text(text = stringResource(id = R.string.welcome_back), fontSize = 36.sp)
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Icon", fontSize = 180.sp, modifier = Modifier.align(Alignment.Center))
            Icon(
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = "Icon",
                modifier = Modifier.align(Alignment.TopEnd),
                tint = Color.Unspecified,
            )
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            items(uiState.waterIntakes) { waterIntake ->
                Text(text = waterIntake.amount.toString())
                Text(text = waterIntake.time.orEmpty())
            }
        }
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


@Preview
@Composable
fun HomeScreenPreview() {

}