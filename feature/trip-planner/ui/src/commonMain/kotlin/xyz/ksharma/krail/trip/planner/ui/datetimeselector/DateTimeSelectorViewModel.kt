package xyz.ksharma.krail.trip.planner.ui.datetimeselector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import xyz.ksharma.krail.core.analytics.Analytics
import xyz.ksharma.krail.core.analytics.AnalyticsScreen
import xyz.ksharma.krail.core.analytics.event.trackScreenViewEvent

class DateTimeSelectorViewModel(
    private val analytics: Analytics,
) : ViewModel() {

    private val _uiState: MutableStateFlow<Unit> = MutableStateFlow(Unit)
    val uiState: StateFlow<Unit> = _uiState
        .onStart {
            analytics.trackScreenViewEvent(screen = AnalyticsScreen.PlanTrip)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Unit)
}
