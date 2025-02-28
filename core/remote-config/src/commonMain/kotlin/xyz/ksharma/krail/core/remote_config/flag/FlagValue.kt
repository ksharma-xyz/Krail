package xyz.ksharma.krail.core.remote_config.flag

sealed class FlagValue {
    data class BooleanValue(val value: Boolean) : FlagValue()
    data class StringValue(val value: String) : FlagValue()
}

fun FlagValue.asBoolean(): Boolean {
    return when (this) {
        is FlagValue.BooleanValue -> this.value
        is FlagValue.StringValue -> this.value.toBoolean()
    }
}
