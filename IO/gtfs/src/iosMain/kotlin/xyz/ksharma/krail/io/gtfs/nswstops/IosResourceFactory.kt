package xyz.ksharma.krail.io.gtfs.nswstops

import platform.Foundation.NSBundle

class IosResourceFactory : ResourceFactory {
    override fun getResourcePath(fileName: String): String? {
        return NSBundle.mainBundle.pathForResource(fileName, "pb")
    }
}
