plugins {
    `kotlin-dsl`
}

group = "com.rssfeed.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplicationPlugin") {
            id = "rssfeed.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("composeConventionPlugin") {
            id = "rssfeed.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("libraryConventionPlugin") {
            id = "rssfeed.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}