enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "Krail"
include(":android-app")
include(":core:design-system")
include(":composeApp")
/*
include(":core:coroutines-ext")
include(":core:date-time")
include(":core:di")
include(":core:network")
include(":feature:trip-planner:network:api")
include(":feature:trip-planner:network:real")
include(":feature:trip-planner:state")
include(":feature:trip-planner:ui")
include(":sandook:api")
include(":sandook:real")
*/
