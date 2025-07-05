package pl.matis.customlauncher.common

import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override val default: CoroutineDispatcher = Dispatchers.Default
}
