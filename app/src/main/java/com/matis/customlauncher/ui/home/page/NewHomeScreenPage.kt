package com.matis.customlauncher.ui.home.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NewHomeScreenPage() {
    Box(modifier = Modifier.Companion.fillMaxSize()) {
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(
                    color = Color.Companion.Black.copy(alpha = .4f),
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Companion.Center
        ) { }
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(Color.Companion.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.Companion.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.Companion.White,
                contentDescription = null
            )
        }
    }
}
