package xyz.ksharma.krail.version

import org.koin.core.module.Module

interface AppVersionProvider {
    fun getAppVersion(): String
}

expect val appVersionModule: Module
