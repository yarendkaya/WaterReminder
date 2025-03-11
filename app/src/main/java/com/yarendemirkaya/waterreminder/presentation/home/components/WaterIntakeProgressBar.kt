package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WaterIntakeProgressBar(successPercentage: Float) {
    Box(
        modifier = Modifier
            .size(150.dp), // Progress bar boyutu
        contentAlignment = Alignment.Center
    ) {
        // Dıştaki progress bar (hedefi tamamlamak için)
        CircularProgressIndicator(
            progress = 1f,
            color = Color.Gray.copy(alpha = 0.3f),
            strokeWidth = 8.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )

        // İçteki progress bar (su içme miktarını göstermek için)
        CircularProgressIndicator(
            progress = successPercentage,
            modifier = Modifier
                .size(150.dp), // Boyut aynı olmalı
            color = Color.Blue, // Başarı rengini belirliyoruz
            strokeWidth = 12.dp, // Barın genişliği
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )

        Text(
            text = "${(successPercentage ).toInt()}%",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


