package com.yarendemirkaya.waterreminder.presentation.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.presentation.statistics.monthly.MonthlyStatisticsContract
import com.yarendemirkaya.waterreminder.presentation.statistics.monthly.MonthlyStatisticsScreen
import com.yarendemirkaya.waterreminder.presentation.statistics.weekly.WeeklyStatisticsContract
import com.yarendemirkaya.waterreminder.presentation.statistics.weekly.WeeklyStatisticsScreen
import kotlinx.coroutines.launch

@Composable
fun StatisticsViewPager(
    weeklyStatisticsUiState: WeeklyStatisticsContract.WeeklyStatisticsUiState,
    monthlyStatisticsUiState: MonthlyStatisticsContract.MonthlyStatisticsUiState
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Text("Statistics")
        }
        TabRow(selectedTabIndex = pagerState.currentPage) {
            listOf("Weekly", "Monthly").forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }
                )
            }
        }

        HorizontalPager(state = pagerState, userScrollEnabled = true) { page ->
            when (page) {
                0 -> WeeklyStatisticsScreen(uiState = weeklyStatisticsUiState)
                1 -> MonthlyStatisticsScreen(uiState = monthlyStatisticsUiState)
            }
        }
    }
}

//@Preview
//@Composable
//fun StatisticsViewPagerPreview() {
//    StatisticsViewPager()
//}

//bir sayı tek mi çift mi olduğunu yazdır

//fun main() {
//
//
//
//    val numberList= arrayOf(1,2,3,4,5,6,7)
//
//    var toplam=0
//    numberList.forEach{number ->
//        toplam=toplam+number
//
//    }
//    println(toplam)
//
//
//
//
//    val sum=0
//
//    numberList.forEach{
//
//        if(numberList[it]%2==1){
//            sum=sum+it
//        }
//    }
//
//    println(sum)
//
//
//
//    val number=0
//    numberList.forEach{
//        if(numberList[it]> number)
//            number= numberList[it]
//    }
//
//    println(number)
//
//
//
//    val ciftSayilar= emptyList()
//    val tekSayilar= emptyList()
//
//    numberList.forEach{
//        if(numberList[it]%2==0){
//            ciftSayilar.add(numberList[it])
//        }else{
//            tekSayilar.add(numberList[it])
//        }
//    }
//
//    println(ciftSayilar)
//    println(tekSayilar)
//
//
//}