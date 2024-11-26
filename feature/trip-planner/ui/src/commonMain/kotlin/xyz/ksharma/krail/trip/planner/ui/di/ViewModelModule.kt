package xyz.ksharma.krail.trip.planner.ui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.ksharma.krail.trip.planner.ui.savedtrips.SavedTripsViewModel
import xyz.ksharma.krail.trip.planner.ui.searchstop.SearchStopViewModel
import xyz.ksharma.krail.trip.planner.ui.timetable.TimeTableViewModel
import xyz.ksharma.krail.trip.planner.ui.usualride.UsualRideViewModel
import xyz.ksharma.krail.trip.planner.ui.settings.SettingsViewModel

val viewModelsModule = module {
    viewModelOf(::SavedTripsViewModel)
    viewModelOf(::SearchStopViewModel)
    viewModelOf(::TimeTableViewModel)
    viewModelOf(::UsualRideViewModel)
    viewModelOf(::SettingsViewModel)
}
