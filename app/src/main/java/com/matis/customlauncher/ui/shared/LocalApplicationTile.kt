package com.matis.customlauncher.ui.shared

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ApplicationTile(
    val fontSize: TextUnit = 8.sp,
    val iconSize: Dp = 32.dp
)

val LocalApplicationTile = compositionLocalOf<ApplicationTile> { ApplicationTile() }
