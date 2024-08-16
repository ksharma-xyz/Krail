package xyz.ksharma.krail.network


interface SydneyTrainApiService : BaseApiService {

    suspend fun getTrains(): List<Train> {
        val request = get("trains")
        return execute(request, List::class.java) as List<Train>
    }
}

data class Train(val x: String)
