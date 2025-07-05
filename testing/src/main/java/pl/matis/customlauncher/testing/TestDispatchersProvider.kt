package pl.matis.customlauncher.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import pl.matis.customlauncher.common.DispatcherProvider


@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider(
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher(),
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher()
) : DispatcherProvider
