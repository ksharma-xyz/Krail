plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.database.real"
}

dependencies {
    implementation(projects.krail.feature.sydneyTrains.database.api)
    implementation(projects.core.di)

    implementation(libs.sqlite.android.driver)
}
