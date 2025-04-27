package com.matis.customlauncher.model

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

fun HomeScreenApplicationDto.toView(): HomeScreenApplicationViewItem.ApplicationItem =
    HomeScreenApplicationViewItem.ApplicationItem(
        packageName = packageName,
        label = label,
        position = position
    )
