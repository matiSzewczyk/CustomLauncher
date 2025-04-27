package com.matis.customlauncher.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {}
        Box {
            BottomInteractionRow()
        }
    }
}

@Composable
fun BottomInteractionRow() {
    Row(
        modifier = Modifier
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
fun EditModeInteractionButton(
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
