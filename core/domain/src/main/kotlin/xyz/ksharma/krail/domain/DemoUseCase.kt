package xyz.ksharma.krail.domain

import xyz.ksharma.krail.data.repository.RealTimeDataRepository
import javax.inject.Inject

/**
 * [DemoUseCase] will fetch the data from [RealTimeDataRepository] and map the
 * data model objects into domain model.
 *
 * It will also wrap the data into a [Result], so as to provide Error, Success and Loading
 * states for UI.
 */
interface DemoUseCase {
    suspend operator fun invoke()
}

class DemoUseCaseImpl @Inject constructor(
    private val realTimeDataRepository: RealTimeDataRepository,
) : DemoUseCase {
    override suspend operator fun invoke() {
        realTimeDataRepository.getSydneyTrains()
    }
}
