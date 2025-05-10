package com.matis.customlauncher.model.domain

data class ApplicationIconConfigDto(
    val iconShape: String, // TODO: Replace with enum class
    val showLabel: Boolean
) {

    companion object {
        val defaults = ApplicationIconConfigDto(
            iconShape = "square",
            showLabel = true
        )
    }
}
