package com.matis.customlauncher.model.view

import androidx.compose.runtime.Stable
import com.matis.customlauncher.model.domain.ApplicationInfoDto

@Stable
data class ApplicationInfoViewDto(
    val packageName: String,
    val label: String,
    val isHomeScreenApplication: Boolean
)

fun ApplicationInfoDto.toView(isHomeScreenApplication: Boolean): ApplicationInfoViewDto =
    ApplicationInfoViewDto(
        packageName = packageName,
        label = label,
        isHomeScreenApplication = isHomeScreenApplication
    )

fun ApplicationInfoViewDto.toDomain(): ApplicationInfoDto =
    ApplicationInfoDto(
        packageName = packageName,
        label = label
    )
