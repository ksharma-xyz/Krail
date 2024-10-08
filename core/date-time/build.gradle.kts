plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.core.dateTime"
}
dependencies {
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.googleTruth)
}
