package xyz.ksharma.feature1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import xyz.ksharma.feature1.DemoUiState
import xyz.ksharma.krail.domain.DemoUseCase
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val demoUseCase: DemoUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DemoUiState> =
        MutableStateFlow(DemoUiState.Loading)
    val uiState: StateFlow<DemoUiState> = _uiState

    init {
        getDemoData()
    }

    @VisibleForTesting
    fun getDemoData() {
        viewModelScope.launch {
            demoUseCase().collectLatest { result ->
                if (result.isSuccess) {
                    _uiState.value = DemoUiState.Success(result.getOrNull()!!)
                } else {
                    _uiState.value = DemoUiState.Error
                }
            }
        }
    }
}
