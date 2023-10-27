package com.tvink28.weather.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tvink28.weather.ui.theme.CenterColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            indicator = { _ -> },
            divider = { }
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                    text = {
                        Text(text = text)
                    },
                    modifier = Modifier.background(if (tabIndex == index) Color.White else Color.Transparent),
                    selectedContentColor = CenterColor,
                    unselectedContentColor = Color.White
                )
            }
        }
    }

    HorizontalPager(pageCount = tabList.size,
        state = pagerState) {

    }
}
