import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("UNUSED")
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val javaVersion = libs.findVersion("java").get().toString().toInt()
            val minSdkVersion = libs.findVersion("minSdk").get().toString().toInt()
            val compileSdkVersion = libs.findVersion("compileSdk").get().toString().toInt()

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = compileSdkVersion

                defaultConfig {
                    minSdk = minSdkVersion
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.values()[javaVersion - 1]
                    targetCompatibility = JavaVersion.values()[javaVersion - 1]
                }

                tasks.withType<KotlinCompile>().configureEach {
                    compilerOptions {
                        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
                        freeCompilerArgs.add("-opt-in=kotlinx.coroutines.FlowPreview")
                        freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
                    }
                }

                buildFeatures {
                    buildConfig = true
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}
