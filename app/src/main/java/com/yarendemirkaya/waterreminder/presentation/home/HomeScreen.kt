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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.yarendemirkaya.waterreminder.data.models.WaterIntake

@Composable
fun HomeScreen(
    uiState: HomeContract.HomeUiState,
    onAction: (HomeContract.HomeUiAction) -> Unit
) {

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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = app_color),
                contentColor = Color.White
            ), onClick = {
                onAction(
                    HomeContract.HomeUiAction.AddWaterIntake(
                        WaterIntake(
                            amount = 250,
                            time = System.currentTimeMillis().toString()
                        )
                    )
                )
            }) {
                Text(text = stringResource(id = R.string.add_water))
            }
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = app_color),
                contentColor = Color.White
            ), onClick = { }) {
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
            items(10) {
                Text(text = "Item $it")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
}