package xyz.ksharma.krail.core.remote_config.flag

import xyz.ksharma.krail.core.log.log
import xyz.ksharma.krail.core.remote_config.RemoteConfig

internal class RemoteConfigFlag(private val remoteConfig: RemoteConfig) : Flag {

    override fun getFlagValue(key: String): FlagValue {
        val configValue = remoteConfig.getConfigValue(key)
        log("RemoteConfigFlag: getFlagValue: key=$key, configValue=$configValue")

        return when (configValue.asString().toBooleanStrictOrNull()) {
            true, false -> FlagValue.BooleanValue(configValue.asBoolean())
            else -> FlagValue.StringValue(configValue.asString())
        }
    }
}
