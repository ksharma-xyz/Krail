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
include(":core:date-time")
include(":core:di")
include(":core:design-system")
include(":core:network")
include(":core:utils")
include(":feature:trip-planner:network:api")
include(":feature:trip-planner:network:real")
include(":feature:trip-planner:state")
include(":feature:trip-planner:ui")
include(":sandook:api")
include(":sandook:real")
