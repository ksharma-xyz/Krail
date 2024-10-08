plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.trip_planner.ui"
}

dependencies {
    implementation(projects.core.dateTime)
    implementation(projects.core.designSystem)
    implementation(projects.feature.tripPlanner.network.api)
    implementation(projects.feature.tripPlanner.state)

    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}
