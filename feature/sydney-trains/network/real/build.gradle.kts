plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.network.real"
}

dependencies {
    implementation(projects.feature.sydneyTrains.network.api)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
}
