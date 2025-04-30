package com.matis.customlauncher.core.data.repository

import kotlinx.coroutines.flow.StateFlow

interface EnvironmentRepository {
    fun isDefaultHomeApplication(): StateFlow<Boolean>
    fun updateDefaultHomeApplicationStatus()
}
