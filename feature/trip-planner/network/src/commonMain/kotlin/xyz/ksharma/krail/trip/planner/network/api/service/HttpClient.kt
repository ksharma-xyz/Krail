package xyz.ksharma.krail.trip.planner.network.api.service

import io.ktor.client.HttpClient

expect fun httpClient(): HttpClient
