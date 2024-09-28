plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.cash.paparazzi)
}

android {
    namespace = "xyz.ksharma.krail.design.system"
}

dependencies {
    api(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    api(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3) // adding for reading code inspiration.

    androidTestImplementation(platform(libs.compose.bom))
    debugApi(libs.compose.ui.tooling)
    debugApi(libs.compose.ui.test.manifest)
}
