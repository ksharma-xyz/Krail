plugins {
    alias(libs.plugins.krail.jvm.library)
    alias(libs.plugins.wire)
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
