package com.yarendemirkaya.waterreminder.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home Screen", modifier = Modifier.weight(1f))
    }
}