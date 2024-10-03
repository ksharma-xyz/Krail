plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.ksharma.krail.nav"
}

dependencies {
    implementation(libs.compose.navigation)
    implementation(libs.kotlinx.serialization.json)
}
