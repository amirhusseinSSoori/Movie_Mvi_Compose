plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id ("com.squareup.sqldelight")
}

android {
    compileSdk = (31)

    defaultConfig {
        applicationId = ("com.example.movie_mvi_compose")
        minSdk = (21)
        targetSdk = (31)
        versionCode = (1)
        versionName = ("1.0")

        testInstrumentationRunner = ("com.example.movie_mvi_compose.AppTestRunner")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {


        getByName("release") {
            isMinifyEnabled = false
            // minifyEnabled false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = ("1.8")
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ("1.1.1")
    }


}

dependencies {
    dep.Main.apply {
        implementation(core_kts)
        implementation(appcompat)
        implementation(material)
        testImplementation(junit)
        androidTestImplementation(junit_test)
        androidTestImplementation(espresso)

    }
    dep.Compose.apply {
        implementation(ui)
        implementation(material)
        implementation(ui_tooling)
        implementation(livedata)
        androidTestImplementation(ui_test)
        implementation(constraintlayout)
        implementation(activity)
    }
    dep.Square.apply {
        implementation(retrofit)
        implementation(converter_gson)
        implementation(okhttp3)
        implementation(interceptor)
    }
    dep.Hilt.apply {
        implementation(hilt)
        kapt(dagger_compiler)
        implementation(viewmodel)
        implementation(navigation)
        kapt(hilt_compiler)
    }

    dep.LifeCycle.apply {
        implementation(viewmodel)
        implementation(runtime)
        implementation(livedata)
    }

    dep.SqlDelight.apply {
        implementation(driver)
        implementation(coroutines)
    }
    dep.KotlinX.apply {
        implementation(core)
        implementation(android)
    }
    dep.Coil.apply {
        implementation(coil)
        implementation(landscapist)
    }

    dep.Util.apply {
        implementation(lotti)
    }
    dep.Navigation.apply {
        implementation(navigation_accompanist)
        implementation(navigation_hilt_compose)
    }
    // unit test
    implementation ("com.dropbox.mobile.store:store4:4.0.5")
    testImplementation ("androidx.test:core:1.4.0")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation ("org.mockito:mockito-inline:3.11.2")
    testImplementation ("app.cash.turbine:turbine:0.8.0")
    testImplementation ("org.robolectric:robolectric:4.7.3")
    androidTestImplementation ("com.google.truth:truth:1.1.3")
    androidTestImplementation ("com.android.support.test:runner:1.3.0-beta01")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptTest ("com.google.dagger:hilt-android-compiler:2.40.5")


    // For instrumented tests.
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.40.5")
    // ...with Java.
    androidTestAnnotationProcessor ("com.google.dagger:hilt-android-compiler:2.40.5")

}

kapt {
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}