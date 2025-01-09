import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class QualityConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            configureKtlint()
        }
    }

    private fun Project.configureKtlint() {
        pluginManager.apply(libs.plugins.ktlint)
        extensions.configure<KtlintExtension> {
            debug.set(true)
            version.set(libs.versions.ktlint)
            reporters {
                reporter(ReporterType.HTML)
                reporter(ReporterType.PLAIN)
            }
        }
    }
}
