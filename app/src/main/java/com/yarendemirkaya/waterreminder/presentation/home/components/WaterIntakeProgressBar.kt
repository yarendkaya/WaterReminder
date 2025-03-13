package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yarendemirkaya.waterreminder.R

@Composable
fun WaterIntakeProgressBar(successPercentage: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = successPercentage/100 ,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "progress_animation"
    )

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.progress_animation)
    )

    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { animatedProgress/2},
            modifier = Modifier.size(160.dp)
        )

        Text(
            text = "${successPercentage.toInt()}%",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}





@Preview
@Composable
fun WaterIntakeProgressBarPreview() {
    WaterIntakeProgressBar(100f)
}

