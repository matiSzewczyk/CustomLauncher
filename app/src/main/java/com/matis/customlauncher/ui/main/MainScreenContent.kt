package com.matis.customlauncher.ui.main

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
) {
    var endOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var startOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var foo by remember { mutableStateOf(false) }
    val minSwipeDistancePx = with(LocalDensity.current) { 80.dp.toPx() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { startOffset = it;endOffset = it },
                    onDragEnd = {
                        println("start: $startOffset")
                        println("end: $endOffset")
                        println(endOffset.y - startOffset.y)
                        when {
                            startOffset.y - endOffset.y > minSwipeDistancePx -> println("swiped up")
                            startOffset.y - endOffset.y < -minSwipeDistancePx -> println("swiped down")
                        }
                    },
                    onVerticalDrag = { pointerInputChange, dragAmount ->
                        endOffset = endOffset.plus(Offset(0f, dragAmount))
                    }
                )
            }
    ) {
        if (foo) Text("swiped up")
        else Text("swiped down")
    }
}
