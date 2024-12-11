package xyz.ksharma.krail.gtfs_static

/**
 * All other transport modes:
 * Swagger: https://opendata.transport.nsw.gov.au/data/dataset/public-transport-timetables-realtime/resource/9b3bfa13-0053-4008-8575-e30151f05d54
 *
 * Metro:
 * v2 API: https://opendata.transport.nsw.gov.au/data/dataset/public-transport-timetables-realtime-v2/resource/ad58515a-3593-4d72-952e-a49c859e1db8
 */
interface NswGtfsService {

    suspend fun getSydneyTrains()

    suspend fun getSydneyMetro()

    suspend fun getLightRail()

    suspend fun getNswTrains()

    suspend fun getSydneyFerries()
}
