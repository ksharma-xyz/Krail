plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.trip.planner.network.real"
}

dependencies {
    implementation(projects.core.coroutinesExt)
    implementation(projects.feature.tripPlanner.network.api)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.retrofit)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.kotlinxCoroutineTest)
}
