plugins {
    alias(libs.plugins.krail.jvm.library)
    alias(libs.plugins.cash.sqldelight)
}

sqldelight {
    databases {
        create("Sandook") {
            packageName.set("xyz.ksharma.sandook.real")
        }
    }
}

dependencies {
    implementation(projects.sandook.api)

    implementation(libs.kotlinx.serialization.json)
}
