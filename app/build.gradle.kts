plugins {
    alias(libs.plugins.rssfeed.application)
    alias(libs.plugins.rssfeed.compose)
}

android {
    namespace = "com.rssfeed"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
