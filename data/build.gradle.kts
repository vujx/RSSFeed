plugins {
    alias(libs.plugins.rssfeed.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.rssfeed.sqldelight)
}

android {
    namespace = "com.rssfeed.data"
}

dependencies {
    implementation(projects.domain)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(platform(libs.ktor.bom))
    implementation(libs.bundles.ktor)
}
