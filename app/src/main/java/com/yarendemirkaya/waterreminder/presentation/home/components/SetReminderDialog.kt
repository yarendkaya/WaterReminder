package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R.color.dark_gray

@Composable
fun SetReminderDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var interval by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Hatırlatma Süresi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Kaç dakika aralıklarla su içme hatırlatması almak istersiniz?")

                OutlinedTextField(
                    value = interval,
                    onValueChange = { interval = it.filter { char -> char.isDigit() } },
                    label = { Text("Dakika") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val minutes = interval.toIntOrNull() ?: 0
                    if (minutes > 0) {
                        onConfirm(minutes)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = dark_gray),
                    contentColor = Color.White
                )
            ) {
                Text("Kaydet")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("İptal")
            }
        }
    )
}
