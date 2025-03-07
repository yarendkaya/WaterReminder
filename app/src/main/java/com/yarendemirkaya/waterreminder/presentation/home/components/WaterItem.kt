package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.R.color.app_color
import com.yarendemirkaya.waterreminder.data.models.WaterIntake

@Composable
fun WaterItem(waterIntake: WaterIntake, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = waterIntake.amount.toString())
        Text(text = waterIntake.time.orEmpty())
        Icon(
            painter = painterResource(id = R.drawable.delete_svgrepo_com__1_),
            contentDescription = "Delete",
            modifier = Modifier.clickable { onDeleteClick() },
            tint = colorResource(id = app_color)
        )
    }
}