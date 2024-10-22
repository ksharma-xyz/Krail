plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.core.datetime"
}
dependencies {
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.googleTruth)
    implementation(libs.kotlinx.datetime)
}
