import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import app.cash.sqldelight.gradle.SqlDelightExtension
import org.gradle.kotlin.dsl.dependencies

class SqlDelightConventionPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project) {
      pluginManager.apply(libs.plugins.sqldelight)

      extensions.configure<SqlDelightExtension> {
        databases.create("Database") {
          packageName.set("com.rssfeed.data")
          generateAsync.set(true)
          schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
          verifyMigrations.set(true)
        }
      }

      dependencies {
        add("implementation", libs.sqldelight.coroutines)
      }
    }
  }
}
