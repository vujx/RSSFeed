import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply(libs.plugins.android.application)

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }
        }
    }

    private fun Project.configureAndroidCompose(
        applicationExtension: ApplicationExtension,
    ) {
        applicationExtension.apply {
            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
            }

            dependencies {
                val bom = libs.compose.bom
                add("implementation", platform(bom))
                add("implementation", libs.bundles.compose)
            }
        }
    }
}
