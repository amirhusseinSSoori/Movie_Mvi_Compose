package dep

object Hilt{
    val hilt ="com.google.dagger:hilt-android:${Version.hilt_version}"
    val dagger_compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt_version}"
    val viewmodel ="androidx.hilt:hilt-lifecycle-viewmodel:${Version.hilt_lifecycle_version}"
    val hilt_compiler="androidx.hilt:hilt-compiler:${Version.hilt_compiler_version}"
    val navigation ="androidx.hilt:hilt-navigation-compose:${Version.hilt_lifecycle_version}"
}