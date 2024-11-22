package xyz.ksharma.krail.trip.planner.network.api

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@Component
abstract class NetworkComponent

@KmpComponentCreate
expect fun createNetworkComponent(): NetworkComponent
