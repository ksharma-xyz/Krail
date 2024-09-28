plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.cash.paparazzi)
}

android {
    namespace = "xyz.ksharma.krail.design.system"
}

dependencies {
    api(platform(libs.compose.bom))
    api(libs.compose.foundation)
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.ui.tooling.preview)

    androidTestImplementation(platform(libs.compose.bom))
    debugApi(libs.compose.ui.tooling)
    debugApi(libs.compose.ui.test.manifest)
}
