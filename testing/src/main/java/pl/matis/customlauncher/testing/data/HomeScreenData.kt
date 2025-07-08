package pl.matis.customlauncher.testing.data

import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.view.HomeScreenItemDto
import com.matis.customlauncher.model.view.HomeScreenPageViewDto
import com.matis.customlauncher.model.view.HomeScreenViewDto

val homeScreenViewData = HomeScreenViewDto(
    layoutType = HomePageLayoutType.GRID_4X4,
    pages = listOf(
        HomeScreenPageViewDto.Applications(
            position = 1,
            items = listOf(
                HomeScreenItemDto.Application(
                    position = 0,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 1,
                    packageName = "com.example",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 2,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 3,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 4,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 5,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 6,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 7,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 8,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 9,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 10,
                    packageName = "",
                    label = ""
                ),
                HomeScreenItemDto.Application(
                    position = 11,
                    packageName = "",
                    label = ""
                ),
            )
        )
    )
)
