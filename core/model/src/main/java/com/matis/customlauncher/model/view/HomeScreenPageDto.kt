package com.matis.customlauncher.model.view

import com.matis.customlauncher.model.domain.HomeScreenPageDto

sealed class HomeScreenPageViewDto(
    open val position: Int
) {

    data class Applications(
        override val position: Int,
        val items: List<HomeScreenItemDto>,
    ) : HomeScreenPageViewDto(position = position)

    data class AddNew(
        override val position: Int
    ) : HomeScreenPageViewDto(position = position)
}

fun HomeScreenPageDto.toView(): HomeScreenPageViewDto {
    val items = buildList {
        addAll(folders.map { it.toView() })
        addAll(applications.map { it.toView() })
    }
    return HomeScreenPageViewDto.Applications(
        position = position,
        items = items
    )
}
