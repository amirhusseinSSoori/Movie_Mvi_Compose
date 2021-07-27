package dep

import Version


object Main {
    val core_kts="androidx.core:core-ktx:${Version.core_ktx_version}"
    val appcompat="androidx.appcompat:appcompat:${Version.appcompat_version}"
    val material = "com.google.android.material:material:${Version.material_version}"
    val espresso ="androidx.test.espresso:espresso-core:${Version.experso_version}"
    val junit_test="androidx.test.ext:junit:${Version.junit_version}"
    val junit="junit:junit:4.+"
}