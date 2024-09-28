plugins {
    alias(libs.plugins.krail.android.library.compose)
    alias(libs.plugins.cash.paparazzi)
}

android {
    namespace = "xyz.ksharma.krail.design.system"
}

dependencies {
    api(platform(libs.compose.bom))
    api(libs.compose.navigation)
    api(libs.material3)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)

    androidTestImplementation(platform(libs.compose.bom))
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)
}
