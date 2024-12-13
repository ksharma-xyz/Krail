package xyz.ksharma.krail.trip.planner.ui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.ksharma.krail.trip.planner.ui.savedtrips.SavedTripsViewModel
import xyz.ksharma.krail.trip.planner.ui.searchstop.SearchStopViewModel
import xyz.ksharma.krail.trip.planner.ui.settings.SettingsViewModel
import xyz.ksharma.krail.trip.planner.ui.timetable.TimeTableViewModel
import xyz.ksharma.krail.trip.planner.ui.alerts.ServiceAlertsViewModel
import xyz.ksharma.krail.trip.planner.ui.themeselection.ThemeSelectionViewModel

val viewModelsModule = module {
    viewModelOf(::SavedTripsViewModel)
    viewModelOf(::SearchStopViewModel)
    viewModelOf(::TimeTableViewModel)
    viewModelOf(::ThemeSelectionViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ServiceAlertsViewModel)
}
