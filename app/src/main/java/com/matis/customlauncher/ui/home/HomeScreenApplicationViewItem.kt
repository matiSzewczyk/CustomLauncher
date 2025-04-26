package com.matis.customlauncher.ui.home

import com.matis.customlauncher.model.HomeScreenApplicationDto
import com.matis.customlauncher.ui.home.HomeScreenApplicationViewItem.ApplicationItem

sealed class HomeScreenApplicationViewItem(
    open val position: Int
) {
    data class ApplicationItem(
        override val position: Int,
        val packageName: String,
        val label: String
    ) : HomeScreenApplicationViewItem(position)

    data class EmptyItem(
        override val position: Int
    ) : HomeScreenApplicationViewItem(position)
}

fun HomeScreenApplicationDto.toView(): ApplicationItem =
    ApplicationItem(
        packageName = packageName,
        label = label,
        position = position
    )
