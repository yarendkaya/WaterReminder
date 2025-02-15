package com.yarendemirkaya.waterreminder.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    result: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state) {
            this@collectWithLifecycle.collect { effect ->
                result(effect)
            }
        }
    }
}

//@Composable
//fun <T> ObserveAsEvents(
//    flow: Flow<T>,
//    key1: Any? = null,
//    key2: Any? = null,
//    onEvent: (T) -> Unit
//) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            withContext(Dispatchers.Main.immediate) {
//                flow.collect(onEvent)
//            }
//        }
//    }
//}