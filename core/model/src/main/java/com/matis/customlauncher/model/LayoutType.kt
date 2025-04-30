package com.matis.customlauncher.model

enum class LayoutType(
    val rawName: String,
    val appCap: Int,
    val columns: Int
) {
    GRID_4X4("Grid 4x4", 16, 4),
    GRID_3X4("Grid 3x4", 12, 3),
    GRID_3X5("Grid 3x5", 15, 3),
    LIST("List", Int.MAX_VALUE, 1)
}
