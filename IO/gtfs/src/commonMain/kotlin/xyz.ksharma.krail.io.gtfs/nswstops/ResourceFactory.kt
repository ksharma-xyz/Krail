package xyz.ksharma.krail.io.gtfs.nswstops

interface ResourceFactory {
    fun getResourcePath(fileName: String): String?
}
