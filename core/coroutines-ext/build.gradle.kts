plugins {
    alias(libs.plugins.krail.android.library)
}

android {
    namespace = "xyz.ksharma.krail.coroutines.ext"
}

dependencies {
    implementation(libs.test.androidxCoreKtx)
}
