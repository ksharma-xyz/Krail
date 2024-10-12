plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.krail.android.hilt)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.database.real"
}

dependencies {
    implementation(projects.core.coroutinesExt)
    implementation(projects.core.di)
    implementation(projects.core.utils)
    implementation(projects.krail.feature.sydneyTrains.database.api)

    implementation(libs.sqlite.android.driver)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
}
