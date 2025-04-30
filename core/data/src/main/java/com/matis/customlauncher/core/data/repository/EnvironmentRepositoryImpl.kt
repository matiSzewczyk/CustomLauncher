package com.matis.customlauncher.core.data.repository

import com.matis.customlauncher.applications.ApplicationsApi
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class EnvironmentRepositoryImpl @Inject constructor(
    private val applicationsApi: ApplicationsApi
) : EnvironmentRepository {

    private val isDefaultHomeApp = MutableStateFlow(false)

    override fun isDefaultHomeApplication(): StateFlow<Boolean> =
        isDefaultHomeApp

    override fun updateDefaultHomeApplicationStatus() {
        isDefaultHomeApp.value = applicationsApi.isDefaultHomeApplication()
    }
}
