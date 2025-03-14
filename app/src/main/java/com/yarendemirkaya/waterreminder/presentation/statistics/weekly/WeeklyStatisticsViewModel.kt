package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

import androidx.lifecycle.ViewModel
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeeklyStatisticsViewModel @Inject constructor(private val repository: WaterRepository):ViewModel() {
}