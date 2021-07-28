package dep

import Version


object Main {

    //for classpath
    val gradle="com.android.tools.build:gradle:${Version.gradle_Version}"
    var kotlin="org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin_version}"
    val hilt="com.google.dagger:hilt-android-gradle-plugin:${Version.hilt_version}"




    val core_kts="androidx.core:core-ktx:${Version.core_ktx_version}"
    val appcompat="androidx.appcompat:appcompat:${Version.appcompat_version}"
    val material = "com.google.android.material:material:${Version.material_version}"
    val espresso ="androidx.test.espresso:espresso-core:${Version.experso_version}"
    val junit_test="androidx.test.ext:junit:${Version.junit_version}"
    val junit="junit:junit:4.+"
}