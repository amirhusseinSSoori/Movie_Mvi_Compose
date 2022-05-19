// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {

        dep.Main.apply {
            classpath(gradle)
            classpath(kotlin)
            classpath(hilt)
            classpath(sqldelight)
            classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.21")


        }
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}