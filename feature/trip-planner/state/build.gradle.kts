plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.trip_planner.state"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
    implementation(projects.feature.tripPlanner.domain)
}
