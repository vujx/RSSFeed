plugins {
    alias(libs.plugins.rssfeed.library)
}

android {
    namespace = "com.rssfeed.domain"
}

dependencies {
    implementation(libs.arrow.core)
    implementation(libs.kotlinx.coroutines.core)
}
