package com.matis.customlauncher.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.matis.customlauncher.R

@Composable
fun EditModeHomeContent(
    onBackPressed: () -> Unit
) {
    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = PaddingValues().calculateBottomPadding()),
        verticalArrangement = Arrangement.Bottom
    ) {
        HomeScreenPagerSection(modifier = Modifier.weight(1f))
        BottomInteractionSection()
    }
}

@Composable
fun HomeScreenPagerSection(modifier: Modifier = Modifier) {
    val state = rememberPagerState(pageCount = { 3 })
    HorizontalPager(
        state = state,
        pageSpacing = 32.dp,
        contentPadding = PaddingValues(all = 64.dp),
        modifier = modifier.fillMaxSize()
    ) {
        NewHomeScreenPage()
    }
}

@Composable
private fun BottomInteractionSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        EditModeInteractionButton(
            onClick = {},
            text = "Settings",
            icon = painterResource(R.drawable.settings)
        )
        EditModeInteractionButton(
            onClick = {},
            text = "Wallpaper and style",
            icon = painterResource(R.drawable.wallpaper)
        )
    }
}

@Composable
private fun EditModeInteractionButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pointerInput(null) {
            detectTapGestures(
                onTap = { onClick() }
            )
        }
    ) {
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = null
        )
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun NewHomeScreenPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(alpha = .4f),
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Center
        ) { }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}
