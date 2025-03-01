package xyz.ksharma.core.test.fakes

import xyz.ksharma.krail.core.remote_config.flag.Flag
import xyz.ksharma.krail.core.remote_config.flag.FlagValue

class FakeFlag : Flag {
    private val flagValues = mutableMapOf<String, FlagValue>()

    fun setFlagValue(key: String, value: FlagValue) {
        flagValues[key] = value
    }

    override fun getFlagValue(key: String): FlagValue {
        return flagValues[key] ?: FlagValue.BooleanValue(false)
    }
}
