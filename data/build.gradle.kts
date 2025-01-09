plugins {
    alias(libs.plugins.rssfeed.library)
}

android {
    namespace = "com.rssfeed.data"
}

dependencies {
    implementation(projects.domain)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}