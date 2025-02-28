package xyz.ksharma.krail.core.remote_config.flag

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import xyz.ksharma.krail.core.remote_config.RemoteConfigDefaults

sealed class FlagValue {
    data class BooleanValue(val value: Boolean) : FlagValue()
    data class StringValue(val value: String) : FlagValue()
    data class JsonValue(val value: String) : FlagValue()
    data class NumberValue(val value: Long) : FlagValue()
}

fun FlagValue.asBoolean(): Boolean {
    return when (this) {
        is FlagValue.BooleanValue -> this.value
        is FlagValue.StringValue -> this.value.toBoolean()
        else -> false
    }
}

fun FlagValue.toStopsIdList(): List<String> {
    return when (this) {
        is FlagValue.JsonValue -> Json.parseToJsonElement(this.value).jsonArray.map {
            it.toString().trim('"')
        }

        else -> {
            val defaultJson: String = RemoteConfigDefaults.getDefaults()
                .firstOrNull { it.first == FlagKeys.HIGH_PRIORITY_STOP_IDS.key }?.second as String
                Json.parseToJsonElement(defaultJson).jsonArray.map {
                    it.toString().trim('"')
                }
        }
    }
}
