package xyz.ksharma.krail.sydney.trains.network.api

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

fun main() {
    // Initialize OkHttpClient
    val client = OkHttpClient()

    // Define the API endpoint and parameters
    val apiEndpoint = "https://api.transport.nsw.gov.au/v1/tp/"
    val apiCall = "stop_finder"
    val params = mapOf(
        "outputFormat" to "rapidJSON",
        "type_sf" to "any",
        "name_sf" to "Circular Quay",
        "coordOutputFormat" to "EPSG:4326",
        "anyMaxSizeHitList" to "10"
    )

    // Build the URL with parameters
    val url = buildUrl(apiEndpoint, apiCall, params)

    // Create the request
    val request = Request.Builder()
        .url(url)
        .build()

    // Execute the request
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        // Parse the JSON response
        val jsonResponse = response.body?.string() ?: throw IOException("Empty response body")
        val jsonObject = JSONObject(jsonResponse)

        // Extract locations from the JSON
        val locations = jsonObject.getJSONArray("locations")
        for (i in 0 until locations.length()) {
            val location = locations.getJSONObject(i)
            println(location.getString("name"))
        }
    }
}

// Function to build URL with parameters
private fun buildUrl(endpoint: String, apiCall: String, params: Map<String, String>): String {
    val urlBuilder = StringBuilder(endpoint)
        .append(apiCall)
        .append("?")
    params.forEach { (key, value) ->
        urlBuilder.append(key).append("=").append(value).append("&")
    }
    return urlBuilder.toString().removeSuffix("&")
}
