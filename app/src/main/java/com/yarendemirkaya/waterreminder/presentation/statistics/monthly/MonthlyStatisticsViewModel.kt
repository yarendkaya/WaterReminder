package com.yarendemirkaya.waterreminder.presentation.statistics.monthly

import androidx.lifecycle.ViewModel
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MonthlyStatisticsViewModel @Inject constructor(private val repository: WaterRepository):ViewModel() {
}