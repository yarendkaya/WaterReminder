package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R

@Composable
fun StreakCard(streakCount: Int?) {
    val message = if (streakCount == null || streakCount == 0) {
        "Bir alışkanlık her gün bir adım atarak oluşur. Bugün bir adım at!"
    } else {
        "$streakCount gündür hedefini başarıyla tamamlıyorsun! Harika gidiyorsun 👏"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.background),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            WavyCardBackground()
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Streak Icon",
                    tint = Color.Yellow,
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = message,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun WavyCardBackground() {
    val appColor = colorResource(id = R.color.white)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height * 0.2f)
            cubicTo(
                width * 0.25f, height * 1f,
                width * 0.75f, height * 0.1f,
                width, height * 0.2f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = path,
            color = appColor.copy(alpha = 0.5f),
        )
    }
}

@Preview
@Composable
fun StreakCardPreview() {
    StreakCard(streakCount = 5)
}