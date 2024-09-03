plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.network.api"
}

dependencies {
    api(projects.core.network)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
}
