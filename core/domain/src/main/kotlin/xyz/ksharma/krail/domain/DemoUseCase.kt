package xyz.ksharma.krail.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import xyz.ksharma.krail.data.repository.RealTimeDataRepository
import xyz.ksharma.krail.domain.mapper.toDomainModel
import xyz.ksharma.krail.model.DemoData
import javax.inject.Inject

/**
 * [DemoUseCase] will fetch the data from [RealTimeDataRepository] and map the
 * data model objects into domain model.
 *
 * It will also wrap the data into a [Result], so as to provide Error, Success and Loading
 * states for UI.
 */
interface DemoUseCase {
    suspend operator fun invoke(): Flow<Result<DemoData>>
}

class DemoUseCaseImpl @Inject constructor(
    private val realTimeDataRepository: RealTimeDataRepository,
) : DemoUseCase {
    override suspend operator fun invoke(): Flow<Result<DemoData>> {

        return runCatching {
            realTimeDataRepository.fetchData()
                .map {
                    runCatching { Result.success(it.toDomainModel()) }.getOrElse { exception ->
                        Result.failure(exception)
                    }
                }
        }.getOrElse { exception -> flowOf(Result.failure(exception)) }
    }
}
