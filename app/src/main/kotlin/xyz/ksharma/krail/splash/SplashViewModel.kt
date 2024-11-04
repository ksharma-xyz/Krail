package xyz.ksharma.krail.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.ksharma.krail.sandook.Sandook
import xyz.ksharma.krail.sandook.di.SandookFactory
import xyz.ksharma.krail.trip.planner.ui.state.TransportMode
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sandookFactory: SandookFactory,
) : ViewModel() {

    private val sandook: Sandook = sandookFactory.create(SandookFactory.SandookKey.THEME)

    fun getThemeColor(): String? {
        val productClass = sandook.getInt("selectedMode")
        val mode = TransportMode.toTransportModeType(productClass)
        return mode?.colorCode
    }
}
