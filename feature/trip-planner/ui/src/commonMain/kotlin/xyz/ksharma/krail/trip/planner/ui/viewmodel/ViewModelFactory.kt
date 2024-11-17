package xyz.ksharma.krail.trip.planner.ui.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import xyz.ksharma.krail.trip.planner.ui.savedtrips.SavedTripsViewModel

interface ViewModelFactory<T> {
    fun create(): T
}

// Factory for SavedTripsViewModel
class SavedTripsViewModelFactory(
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModelFactory<SavedTripsViewModel> {
    override fun create(): SavedTripsViewModel {
        return SavedTripsViewModel(ioDispatcher)
    }
}
