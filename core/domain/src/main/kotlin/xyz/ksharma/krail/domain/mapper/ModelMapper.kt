package xyz.ksharma.krail.domain.mapper

import xyz.ksharma.krail.data.model.DemoDataModel
import xyz.ksharma.krail.model.DemoData
import xyz.ksharma.krail.model.Item

internal fun DemoDataModel.toDomainModel() = DemoData(
    list = this.data.map { it.toItemModel() }
)

private fun xyz.ksharma.krail.data.model.Item.toItemModel(): Item = Item(value = value, id = id)

private inline fun <reified T : Enum<T>> safeValueOf(type: String): T? =
    runCatching { java.lang.Enum.valueOf(T::class.java, type) }.getOrNull()
