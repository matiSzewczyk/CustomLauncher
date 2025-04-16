plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.matis.customlauncher.core.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":applications"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
