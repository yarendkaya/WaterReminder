package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R.color.app_color
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import com.yarendemirkaya.waterreminder.presentation.home.HomeContract

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