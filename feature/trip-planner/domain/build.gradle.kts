plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.trip_planner.domain"
}
dependencies {
    implementation(projects.feature.tripPlanner.network.api)
}
