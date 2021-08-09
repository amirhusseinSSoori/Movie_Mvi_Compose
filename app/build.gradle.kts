plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = (30)
    buildToolsVersion = ("30.0.3")

    defaultConfig {
        applicationId = ("com.example.movie_mvi_compose")
        minSdk = (21)
        targetSdk = (30)
        versionCode = (1)
        versionName = ("1.0")

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")
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
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ("1.0.1")
        kotlinCompilerVersion = ("1.5.20")
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

    dep.Room.apply {
        implementation(runtime)
        kapt(compiler)
        implementation(ktx)
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
    implementation("com.google.accompanist:accompanist-navigation-animation:0.16.0")
}

kapt {
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}