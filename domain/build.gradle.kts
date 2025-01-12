plugins {
    alias(libs.plugins.rssfeed.library)
}

android {
    namespace = "com.rssfeed.domain"
}

dependencies {
    implementation(libs.arrow.core)
    implementation(libs.kotlinx.coroutines.core)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(libs.work.manager)
}
