package com.example.audioswitch_equalizedevo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.example.audioswitch_equalizedevo.data.TabItem
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeScreen(navController: NavController, paddingValues: PaddingValues, viewModel: SongsViewModel) {
val tabItems = listOf(
    TabItem(title = "Songs", selectedIcon = Icons.Default.Home, unselectedIcon = Icons.TwoTone.Home),
    TabItem(title = "Favourites", selectedIcon = Icons.Default.Star, unselectedIcon = Icons.TwoTone.Star),
)
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    LaunchedEffect(key1 = pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    Column(modifier = Modifier.padding(paddingValues)){
        TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.height(60.dp).weight(0.08f)){
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = { Text(tabItem.title)},
                    icon = {
                        if (selectedTabIndex == index) {
                            Icon(tabItem.selectedIcon, contentDescription = null)
                        } else {
                            Icon(tabItem.unselectedIcon, contentDescription = null)
                        }
                    }
                )
            }
        }
        HorizontalPager(state = pagerState, Modifier.weight(1f)) { page ->
            when (page) {
                0 -> {
                    SongsScreen(paddingValues = PaddingValues(), viewModel)
                }

                1 -> {
                    Text("Page 2")
                }// TODO Spatial Audio Screen

                2 -> {
                    Text("Page 3")
                }  // TODO Search Screen

                3 -> {
                    Text("Page 4")
                } // TODO Equalizer Screen

            }

        }
        PlayerCompact(navController, viewModel)
    }
}