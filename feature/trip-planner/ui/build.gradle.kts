plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.krail.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.trip.planner.ui"
}

dependencies {
    implementation(projects.core.dateTime)
    implementation(projects.core.designSystem)
    implementation(projects.feature.tripPlanner.network.api)
    implementation(projects.feature.tripPlanner.state)
    implementation(projects.sandook.api)

    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.kotlinx.datetime)
    implementation(projects.sandook.real)
    implementation(libs.compose.material3)

    testImplementation(libs.test.composeUiTestJunit4)
    testImplementation(libs.test.kotlin)
}
