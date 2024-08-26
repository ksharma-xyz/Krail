enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {

    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Krail"
include(":app")
include(":core:coroutines-ext")
include(":core:data")
include(":core:di")
include(":core:domain")
include(":core:design-system")
include(":core:model")
include(":core:network")
include(":feature:feature1")
