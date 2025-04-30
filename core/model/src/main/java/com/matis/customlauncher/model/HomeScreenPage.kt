package com.matis.customlauncher.model

sealed interface HomeScreenPage

data class Applications(val applications: List<HomeScreenApplicationViewItem>) : HomeScreenPage

data object AddNewApplication : HomeScreenPage
