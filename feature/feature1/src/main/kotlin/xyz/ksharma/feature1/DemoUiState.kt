package xyz.ksharma.feature1

import xyz.ksharma.krail.model.DemoData

sealed interface DemoUiState {
    data object Loading : DemoUiState
    data object Error : DemoUiState
    data class Success(val demoData: DemoData) : DemoUiState
}
