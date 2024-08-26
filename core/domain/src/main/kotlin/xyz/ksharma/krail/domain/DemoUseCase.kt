package xyz.ksharma.krail.domain

import xyz.ksharma.krail.data.repository.SydneyTrainsRepository
import javax.inject.Inject

/**
 * [DemoUseCase] will fetch the data from [SydneyTrainsRepository] and map the
 * data model objects into domain model.
 *
 * It will also wrap the data into a [Result], so as to provide Error, Success and Loading
 * states for UI.
 */
interface DemoUseCase {
    suspend operator fun invoke()
}

class DemoUseCaseImpl @Inject constructor(
    private val sydneyTrainsRepository: SydneyTrainsRepository,
) : DemoUseCase {
    override suspend operator fun invoke() {
        sydneyTrainsRepository.fetchStaticSydneyTrainsScheduleAndCache()
    }
}
