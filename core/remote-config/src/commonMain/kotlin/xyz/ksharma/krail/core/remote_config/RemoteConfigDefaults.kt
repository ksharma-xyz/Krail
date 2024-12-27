package xyz.ksharma.krail.core.remote_config

/**
 * Holds the default values for remote configuration.
 * Add new default values here. These will be used as fallbacks when the remote config is not available
 * due to network or other issues.
 */
object RemoteConfigDefaults {

    /**
     * Returns a list of default configuration key-value pairs.
     * These defaults are used as fallbacks when remote config values are not available.
     */
    fun getDefaults(): Array<Pair<String, Any?>> {
        return arrayOf(
            Pair("test", false),
        )
    }
}
