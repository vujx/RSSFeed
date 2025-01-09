plugins {
    alias(libs.plugins.rssfeed.application)
    alias(libs.plugins.rssfeed.compose)
}

android {
    namespace = "com.rssfeed"
}

dependencies {
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
}
