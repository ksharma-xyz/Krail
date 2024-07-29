package xyz.ksharma.krail.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// Data Model
@Serializable
data class DemoDataModel(

    @SerializedName("data")
    val data: List<Item>,
)

// Data Model
@Serializable
data class Item(
    val value: String,
    val id: String,
)
