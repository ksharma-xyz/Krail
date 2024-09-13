plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.trip_planner.network.api"
}

dependencies {
    api(projects.core.network)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.retrofit)
}
