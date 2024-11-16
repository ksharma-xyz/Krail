plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.krail.kotlin.multiplatform)
}

android {
    namespace = "xyz.ksharma.krail.trip.planner.state"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
}
