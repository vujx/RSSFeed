plugins {
    alias(libs.plugins.rssfeed.application)
    alias(libs.plugins.rssfeed.compose)
}

android {
    namespace = "com.rssfeed"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.arrow.core)

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    implementation(libs.sqldelight.android)

    implementation(libs.work.manager)
}
