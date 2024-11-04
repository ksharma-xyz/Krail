package xyz.ksharma.krail.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import xyz.ksharma.krail.di.AppDispatchers
import xyz.ksharma.krail.di.Dispatcher
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sandookFactory: SandookFactory,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.THEME)

    suspend fun isThemeAvailable(): Boolean = withContext(ioDispatcher) {
        val productClass = sandook.getInt("selectedMode")
        val mode = TransportMode.toTransportModeType(productClass)
        !(productClass == 0 || mode == null)
    }
}
