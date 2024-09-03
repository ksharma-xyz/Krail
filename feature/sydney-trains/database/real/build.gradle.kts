plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.cash.sqldelight)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.database.real"
}

sqldelight {
    databases {
        create("KrailDB") {
            packageName.set("xyz.ksharma.krail")
        }
    }
}

dependencies {
    implementation(projects.krail.feature.sydneyTrains.database.api)
    implementation(projects.core.di)

    implementation(libs.sqlite.android.driver)
}
