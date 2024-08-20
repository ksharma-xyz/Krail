package xyz.ksharma.krail.data;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

object ZipParser {

    @Throws(IOException::class)
    fun ByteArray.parseZip() {
        val bais = ByteArrayInputStream(this)
        val zis = ZipInputStream(bais)

        var entry: ZipEntry
        while ((zis.nextEntry.also { entry = it }) != null) {
            if (entry.isDirectory) {
                // Handle directory entry
            } else {
                // Handle file entry
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var length: Int
                while ((zis.read(buffer).also { length = it }) > 0) {
                    baos.write(buffer, 0, length)
                }
                baos.close()
                val fileData = baos.toByteArray()

                // Process the extracted file data
                val fileName = entry.name
                if (fileName.endsWith(".txt")) {
                    val fileContent = String(fileData, charset("UTF-8")) // Assuming UTF-8 encoding
                    println("File: $fileName")
                    println("Content: $fileContent")
                } else {
                    // Handle other file types
                }
            }
        }
        zis.close()
    }
}
