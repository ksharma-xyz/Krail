package xyz.ksharma.krail.sandook

import me.tatarka.inject.annotations.Inject

@Inject
internal class RealSandookDb(factory: SandookFactory) : SandookDb {

    private val database = factory.build()
    private val query = database.krailSandookQueries

    // region Theme
    override fun insertOrReplaceTheme(productClass: Long) {
        query.insertOrReplaceProductClass(productClass)
    }

    override fun getProductClass(): Long? {
        return query.selectProductClass().executeAsOneOrNull()
    }

    override fun clearTheme() {
        query.clearTheme()
    }

    // endregion

    // region SavedTrip
    override fun insertOrReplaceTrip(
        tripId: String,
        fromStopId: String,
        fromStopName: String,
        toStopId: String,
        toStopName: String,
    ) {
        query.insertOrReplaceTrip(
            tripId,
            fromStopId,
            fromStopName,
            toStopId,
            toStopName,
        )
    }

    override fun deleteTrip(tripId: String) {
        query.deleteTrip(tripId)
    }

    override fun selectAllTrips(): List<SavedTrip> {
        return query.selectAllTrips().executeAsList()
    }

    override fun selectTripById(tripId: String): SavedTrip? {
        return query.selectTripById(tripId).executeAsOneOrNull()
    }

    override fun clearSavedTrips() {
        query.clearSavedTrips()
    }
    // endregion
}
