package xyz.ksharma.krail.core.remote_config.flag

interface Flag {
    fun getFlagValue(key: String): FlagValue
}
