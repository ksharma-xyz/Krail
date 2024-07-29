package xyz.ksharma.krail.data.util

import android.content.Context
import com.google.gson.Gson
import xyz.ksharma.krail.data.model.DemoDataModel

internal fun Context.readJsonFromAssets(fileName: String) =
    assets.open(fileName).bufferedReader().use { it.readText() }

internal fun String.parseJsonToDataModel() =
    Gson().fromJson(this, DemoDataModel::class.java)
