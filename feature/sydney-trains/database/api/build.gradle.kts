plugins {
    alias(libs.plugins.krail.android.library)
    alias(libs.plugins.cash.sqldelight)
}

android {
    namespace = "xyz.ksharma.krail.sydney_trains.database.api"
}

sqldelight {
    databases {
        create("KrailDB") {
            packageName.set("xyz.ksharma.krail")
        }
    }
}

dependencies {
}
