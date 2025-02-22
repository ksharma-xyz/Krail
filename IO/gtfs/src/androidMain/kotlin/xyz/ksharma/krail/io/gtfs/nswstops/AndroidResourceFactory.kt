package xyz.ksharma.krail.io.gtfs.nswstops

import android.content.Context

class AndroidResourceFactory(private val context: Context): ResourceFactory {

    override fun getResourcePath(fileName: String): String? {
        return context.filesDir.resolve(fileName).absolutePath
    }
}
