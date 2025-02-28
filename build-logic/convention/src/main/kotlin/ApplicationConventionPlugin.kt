import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationConventionPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project) {
      applyPlugins()

      extensions.configure<ApplicationExtension> {
        configureAndroid(this)
        configureKotlinAndroid(this)
        configureBuildTypes(this)
      }
    }
  }

  private fun Project.applyPlugins() {
    with(pluginManager) {
      apply(libs.plugins.android.application)
      apply(libs.plugins.kotlin.android)
      apply(libs.plugins.rssfeed.quality)
    }
  }

  private fun Project.configureAndroid(
    applicationExtension: ApplicationExtension,
  ) = applicationExtension.apply {
    buildFeatures {
      buildConfig = true
    }

    defaultConfig {
      applicationId = "com.rssfeed"
      targetSdk = libs.versions.targetSdk.get().toInt()

      versionCode = 1
      versionName = "1.0"
    }

    packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
    }
  }
}
