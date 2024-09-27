plugins {
    alias(libs.plugins.krail.jvm.library)
    alias(libs.plugins.wire)
}

dependencies {
/*
    implementation("com.google.protobuf:protobuf-javalite:3.25.3") // Replace with latest version
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.25.3") // Replace with latest version
*/
}

wire {
    kotlin {
        javaInterop = true
        out = "$projectDir/build/generated/source/wire"
        rpcCallStyle = "suspending"
        rpcRole = "client"
        singleMethodServices = false
    }
    protoPath {
        srcDir(files("src/main/proto"))
    }
    sourcePath {
        srcDir(files("src/main/proto"))
    }
}
