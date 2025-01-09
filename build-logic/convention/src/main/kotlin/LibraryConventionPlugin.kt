import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyPlugins()

            extensions.configure<com.android.build.gradle.LibraryExtension> {
                configureKotlinAndroid(this)
                configureBuildTypes(this)
            }
        }
    }

    private fun Project.applyPlugins() {
        with(pluginManager) {
            apply(libs.plugins.android.library)
            apply(libs.plugins.kotlin.android)
        }
    }
}