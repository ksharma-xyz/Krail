plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.compose.multiplatform)
    alias(libs.plugins.krail.kotlin.multiplatform)
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
    implementation(libs.compose.material3)
    implementation(libs.core.ktx) // adding for reading code inspiration.

    // Testing
    androidTestImplementation(platform(libs.compose.bom))
    debugApi(libs.compose.ui.tooling)
    debugApi(libs.test.composeUiTestManifest)
}
