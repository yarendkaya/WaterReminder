package com.yarendemirkaya.waterreminder.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreHelper@Inject constructor(private val context: Context) {
    private val firstLoginKey = booleanPreferencesKey("isFirstLogin")

    val isFirstLogin: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[firstLoginKey] ?: true
    }

    suspend fun setFirstLoginDone() {
        context.dataStore.edit { prefs ->
            prefs[firstLoginKey] = false
        }
    }
}